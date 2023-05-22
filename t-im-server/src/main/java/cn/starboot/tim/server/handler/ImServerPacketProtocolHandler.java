package cn.starboot.tim.server.handler;

import cn.starboot.tim.common.codec.TIMPacketProtocol;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.command.handler.AbstractCmdHandler;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.plugin.TIMPlugin;
import cn.starboot.tim.common.util.TIMLogUtil;
import cn.starboot.tim.server.ImServerChannelContext;
import cn.starboot.tim.server.ImServerConfig;
import cn.starboot.tim.server.TIMServer;
import cn.starboot.tim.server.command.TIMServerCommandManager;
import cn.starboot.tim.server.intf.TIMServerProcessor;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImServerPacketProtocolHandler extends TIMPacketProtocol<ImServerChannelContext> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImServerPacketProtocolHandler.class);

	private final TIMServerCommandManager timServerTIMCommandManager;

	private final TIMPlugin timPlugin = new TIMPlugin();

	public ImServerPacketProtocolHandler() {
		this.timServerTIMCommandManager = TIMServerCommandManager.getTIMServerCommandManagerInstance();
	}

	@Override
	public ImPacket handle(ImServerChannelContext imChannelContext, ImPacket imPacket) {
		// 预处理，验证报文是否合法
		switch (preHandle(imChannelContext, imPacket)) {
			case INVALID: {
				// 投递非法包裹，直接将其断开连接
				TIMServer.close(imChannelContext);
				break;
			}
			case VALID: {
				TIMCommandType timCommandType = imPacket.getTIMCommandType();
				AbstractCmdHandler<ImServerChannelContext, ImServerConfig, TIMServerProcessor> cmdHandler = this.timServerTIMCommandManager.getCommand(timCommandType);
				try {
					if (timPlugin.beforeProcess(imPacket, imChannelContext)) {
						return cmdHandler.handler(imPacket, imChannelContext);
					}
				} catch (ImException | InvalidProtocolBufferException e) {
					e.printStackTrace();
				}
				break;
			}
			case REPEAT: {
				Integer req = imPacket.getReq();
				TIMLogUtil.debug(LOGGER, "Received duplicate packages that req: {}", req);
				ImPacket respImPacket = imChannelContext
						.getConfig()
						.getImPacketFactory()
						.createImPacket(TIMCommandType.COMMAND_REQ_RESP, null, null);
				respImPacket.setResp(req);
				return respImPacket;
			}
		}
		return null;
	}
}
