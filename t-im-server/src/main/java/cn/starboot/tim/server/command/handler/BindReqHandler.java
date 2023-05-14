package cn.starboot.tim.server.command.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.BindPacketProto;
import cn.starboot.tim.common.packet.proto.RespPacketProto;
import cn.starboot.tim.common.util.TIMLogUtil;
import cn.starboot.tim.server.ImServerChannelContext;
import cn.starboot.tim.server.TIM;
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
			LOGGER.error("消息包格式化出错");
			return null;
		}
		imPacket.setTIMCommandType(TIMCommandType.COMMAND_BIND_RESP);
		setRespPacketImStatus(imPacket,
				verify(StrUtil.isNotEmpty(packet.getBindId()),
						StrUtil.isNotBlank(packet.getBindId()),
						imChannelContext
								.getConfig()
								.getProcessor()
								.handleBindPacket(imChannelContext, packet),
						handler(packet, imChannelContext)) ?
						RespPacketProto.RespPacket.ImStatus.BIND_SUCCESS :
						RespPacketProto.RespPacket.ImStatus.BIND_FAILED);
		return imChannelContext.getConfig().getProcessor().beforeSend(imChannelContext, imPacket) ? imPacket : null;
	}

	private boolean handler(BindPacketProto.BindPacket packet, ImServerChannelContext imChannelContext) {
		switch (packet.getOptionType()) {
			case BIND:
				return bindHandler(packet, imChannelContext);
			case UNBIND:
				return unbindHandler(packet, imChannelContext);
			default:
				TIMLogUtil.error(LOGGER, "绑定处理器异常：未指明处理操作类型");
				return false;
		}
	}

	private boolean bindHandler(BindPacketProto.BindPacket packet, ImServerChannelContext imChannelContext) {
		// 进行绑定操作
		switch (packet.getBindType()) {
			case ID:
				return TIM.bindId(packet.getBindId(), imChannelContext);
			case IP:
				return TIM.bindId(packet.getBindId(), imChannelContext);
			case BS_ID:
				return false;
			case GROUP:
				return TIM.bindGroup(packet.getBindId(), imChannelContext);
			case USER:
				return TIM.bindUser(packet.getBindId(), imChannelContext);
			case TOKEN:
				return false;
			default:
				TIMLogUtil.error(LOGGER, "");
				return false;
		}
	}

	private boolean unbindHandler(BindPacketProto.BindPacket packet, ImServerChannelContext imChannelContext) {
		switch (packet.getBindType()) {
			case ID:
				break;
			case IP:
				break;
			case BS_ID:
				break;
			case GROUP:
				break;
			case USER:
				break;
			case TOKEN:
				break;
			default:
				TIMLogUtil.error(LOGGER, "");
		}
		return false;
	}
}
