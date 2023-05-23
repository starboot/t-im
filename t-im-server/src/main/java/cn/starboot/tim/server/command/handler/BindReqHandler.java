package cn.starboot.tim.server.command.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.BindPacketProto;
import cn.starboot.tim.common.packet.proto.RespPacketProto;
import cn.starboot.tim.common.util.TIMLogUtil;
import cn.starboot.tim.server.ImServerChannelContext;
import cn.starboot.tim.server.TIMServer;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by DELL(mxd) on 2021/12/25 15:29
 */
public class BindReqHandler extends AbstractServerCmdHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(BindReqHandler.class);

	@Override
	public TIMCommandType command() {
		return TIMCommandType.COMMAND_BIND_REQ;
	}

	@Override
	public ImPacket handler(ImPacket imPacket, ImServerChannelContext imChannelContext) throws InvalidProtocolBufferException {
		BindPacketProto.BindPacket packet = BindPacketProto.BindPacket.parseFrom(imPacket.getData());
		if (ObjectUtil.isEmpty(packet)) {
			LOGGER.error("BindReqHandler: message formatting error");
			return null;
		}
		imPacket.setTIMCommandType(TIMCommandType.COMMAND_BIND_RESP);
		if (verify(StrUtil.isNotEmpty(packet.getBindId()),
				StrUtil.isNotBlank(packet.getBindId()),
				imChannelContext
						.getConfig()
						.getProcessor()
						.handleBindPacket(imChannelContext, packet),
				handler(packet, imChannelContext))) {
			imPacket.setData(getRespPacket(TIMCommandType.COMMAND_BIND_RESP, RespPacketProto.RespPacket.ImStatus.BIND_SUCCESS, "bind success").toByteArray());
		} else {
			imPacket.setData(getRespPacket(TIMCommandType.COMMAND_BIND_RESP, RespPacketProto.RespPacket.ImStatus.BIND_FAILED, "bind failed").toByteArray());
		}

		return imChannelContext.getConfig().getProcessor().beforeSend(imChannelContext, imPacket) ? imPacket : null;
	}

	private boolean handler(BindPacketProto.BindPacket packet, ImServerChannelContext imChannelContext) {
		switch (packet.getOptionType()) {
			case BIND:
				return bindHandler(packet, imChannelContext);
			case UNBIND:
				return unbindHandler(packet, imChannelContext);
			default:
				TIMLogUtil.error(LOGGER, "BindReqHandler: unknown option type");
				return false;
		}
	}

	private boolean bindHandler(BindPacketProto.BindPacket packet, ImServerChannelContext imChannelContext) {
		switch (packet.getBindType()) {
			case ID:
				return TIMServer.bindId(packet.getBindId(), imChannelContext);
			case IP:
				return TIMServer.joinIP(packet.getBindId(), imChannelContext);
			case BS_ID:
				return TIMServer.bindBsId(packet.getBindId(), imChannelContext);
			case GROUP:
				return TIMServer.joinGroup(packet.getBindId(), imChannelContext);
			case USER:
				return TIMServer.joinUser(packet.getBindId(), imChannelContext);
			case TOKEN:
				return TIMServer.joinToken(packet.getBindId(), imChannelContext);
			default:
				TIMLogUtil.error(LOGGER, "BindReqHandler: bind Type not exist");
				return false;
		}
	}

	private boolean unbindHandler(BindPacketProto.BindPacket packet, ImServerChannelContext imChannelContext) {
		switch (packet.getBindType()) {
			case ID:
				return TIMServer.unbindId(packet.getBindId(), imChannelContext);
			case IP:
				return TIMServer.exitIP(packet.getBindId(), imChannelContext);
			case BS_ID:
				return TIMServer.unbindBsId(packet.getBindId(), imChannelContext);
			case GROUP:
				return TIMServer.exitGroup(packet.getBindId(), imChannelContext);
			case USER:
				return TIMServer.exitUser(packet.getBindId(), imChannelContext);
			case TOKEN:
				return TIMServer.exitToken(packet.getBindId(), imChannelContext);
			default:
				TIMLogUtil.error(LOGGER, "BindReqHandler: unbind Type not exist");
		}
		return false;
	}
}
