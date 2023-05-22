package cn.starboot.tim.client.handler;

import cn.starboot.tim.client.ImClientChannelContext;
import cn.starboot.tim.client.command.TIMClientCommandManager;
import cn.starboot.tim.client.command.handler.AbstractClientCmdHandler;
import cn.starboot.tim.common.codec.TIMPacketProtocol;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.command.handler.AbstractCmdHandler;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.plugin.TIMPlugin;
import cn.starboot.tim.common.util.TIMLogUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class ImClientPacketProtocolHandler extends TIMPacketProtocol<ImClientChannelContext> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImClientPacketProtocolHandler.class);

	private final TIMClientCommandManager timClientCommandManager;

	private final TIMPlugin timPlugin = new TIMPlugin();

	public ImClientPacketProtocolHandler() {
		this.timClientCommandManager = TIMClientCommandManager.getTIMClientCommandManagerInstance();
	}

	@Override
	public ImPacket handle(ImClientChannelContext imChannelContext, ImPacket imPacket) {
		// 预处理，验证报文是否合法
		switch (preHandle(imChannelContext, imPacket)) {
			case INVALID: {
				// 投递非法包裹，直接将其断开连接
//				TIMServer.close(imChannelContext);
				break;
			}
			case VALID: {
				TIMCommandType timCommandType = imPacket.getTIMCommandType();
				AbstractClientCmdHandler cmdHandler = this.timClientCommandManager.getCommand(timCommandType);
				if (cmdHandler == null) {
					TIMLogUtil.error(LOGGER, "unknown cmd instruct : {}", timCommandType);
					break;
				}
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
