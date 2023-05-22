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

import java.util.Set;

public class ImServerPacketProtocolHandler extends TIMPacketProtocol<ImServerChannelContext> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImServerPacketProtocolHandler.class);

	private final TIMServerCommandManager timServerTIMCommandManager;

	private final TIMPlugin timPlugin = new TIMPlugin();

	public ImServerPacketProtocolHandler() {
		this.timServerTIMCommandManager = TIMServerCommandManager.getTIMServerCommandManagerInstance();
	}

	private enum ImPacketEnum {
		// 重复消息包
		REPEAT,

		// 非法消息包
		INVALID,

		// 正常消息包
		VALID
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
				TIMCommandType TIMCommandType = imPacket.getTIMCommandType();
				AbstractCmdHandler<ImServerChannelContext, ImServerConfig, TIMServerProcessor> cmdHandler = this.timServerTIMCommandManager.getCommand(TIMCommandType);
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

	/**
	 * 预处理
	 */
	private ImPacketEnum preHandle(ImServerChannelContext imChannelContext, ImPacket imPacket) {
		// 启用ACK
		if (imChannelContext.getConfig().isAckPlugin()) {
			Integer reqInteger = imChannelContext.getReqInteger();
			Integer req = imPacket.getReq();
			Integer minSynMessagePoolNum = imChannelContext.getMinSynMessagePoolNum();
			Set<Integer> synMessagePool = imChannelContext.getSynMessagePool();
			// 收到理论顺序消息包
			if (reqInteger.equals(req)) {
				return ImPacketEnum.VALID;
			}
			// 收到小于理论顺序包裹，可能存在重复投递或者丢失的消息包
			if (reqInteger > req) {
				if (req < minSynMessagePoolNum) {
					return ImPacketEnum.REPEAT;
				}

				// imChannelContext.getMinMissMessage() <= imPacket.getReq() < imChannelContext.getReqInteger()
				// 此时可能重复，可能是丢失的包裹
				if (synMessagePool.contains(req)) {
					// 确定为丢失的包裹
					synMessagePool.remove(req);
					return ImPacketEnum.VALID;
				} else
					return ImPacketEnum.REPEAT;
			}
			// 收到大于理论顺序的消息包（存在丢包现象，先处理，将丢失的ack进行保存）
			if (req - reqInteger + 1 > imChannelContext.getConfig().getMaximumInterval() - synMessagePool.size()) {
				// 收到的消息包 丢失严重。
				return ImPacketEnum.INVALID;
			} else {
				for (int i = reqInteger; i < req; i++) {
					synMessagePool.add(i);
				}
				return ImPacketEnum.VALID;
			}
		}
		return ImPacketEnum.VALID;
	}

}
