package cn.starboot.tim.server.protocol;

import cn.starboot.tim.common.codec.TIMPacketProtocol;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.command.handler.AbstractCmdHandler;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.plugin.TIMPlugin;
import cn.starboot.tim.server.ImServerChannelContext;
import cn.starboot.tim.server.ImServerConfig;
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
		TIMCommandType TIMCommandType = imPacket.getTIMCommandType();
		AbstractCmdHandler<ImServerChannelContext, ImServerConfig, TIMServerProcessor> cmdHandler = this.timServerTIMCommandManager.getCommand(TIMCommandType);
		try {
			if (timPlugin.beforeProcess(imPacket, imChannelContext)) {
				return cmdHandler.handler(imPacket, imChannelContext);
			}
		} catch (ImException | InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 预处理
	 */
	private ImPacket preHandle(ImServerChannelContext imChannelContext, ImPacket imPacket) {

		// 检查是否重复包
		if (imChannelContext.getReqInteger() > imPacket.getReq()) {
			imPacket.setResp(imPacket.getReq());
			imPacket.setReq(null);
			return imPacket;
		}

		return null;
	}
}
