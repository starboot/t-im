package cn.starboot.tim.server.command.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.ImStatus;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.HistoryMessageProto;
import cn.starboot.tim.common.packet.proto.MessagePacketProto;
import cn.starboot.tim.common.packet.proto.RespPacketProto;
import cn.starboot.tim.common.util.TIMLogUtil;
import cn.starboot.tim.server.ImServerChannelContext;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by DELL(mxd) on 2021/12/25 16:32
 */
public class MessageReqHandler extends AbstractServerCmdHandler {

	private static final Logger log = LoggerFactory.getLogger(MessageReqHandler.class);

	@Override
	public TIMCommandType command() {
		return TIMCommandType.COMMAND_MESSAGE_REQ;
	}

	@Override
	public ImPacket handler(ImPacket imPacket, ImServerChannelContext imChannelContext) throws InvalidProtocolBufferException {

		MessagePacketProto.MessagePacket messagePacket = MessagePacketProto.MessagePacket.parseFrom(imPacket.getData());
		if (ObjectUtil.isEmpty(messagePacket)) {
			log.error("消息包格式化出错");
			return null;
		}
		ImPacket build1 = ImPacket.newBuilder().setTIMCommandType(TIMCommandType.COMMAND_MESSAGE_RESP).build();
		HistoryMessageProto.HistoryMessage.Builder builder1 = HistoryMessageProto.HistoryMessage.newBuilder();
		HistoryMessageProto.HistoryMessage build = builder1.setUserId("1191998028")
				.build();
		// 群组ID;
		String groupId = messagePacket.getGroupId();
		// 当前用户ID;
		String userId = messagePacket.getUserId();
		// 消息来源用户ID;
		String fromUserId = messagePacket.getFromUserId();
		// 消息类型;
		MessagePacketProto.MessagePacket.MessageType messageType = messagePacket.getMessageType();
		// 消息日期
		long beginTime = messagePacket.getBeginTime();
		long endTime = messagePacket.getEndTime();
		int offset = messagePacket.getOffset();
		int count = messagePacket.getCount();
		setRespPacketImStatus(build1,
				verify(StrUtil.isNotBlank(userId),
						ObjectUtil.isNotNull(messageType),
						beginTime > 0,
						endTime > 0,
						offset >= 0,
						count > 0,
						(StrUtil.isNotBlank(groupId) || StrUtil.isNotBlank(fromUserId))
				) ? ObjectUtil.equal(messagePacket, MessagePacketProto.MessagePacket.MessageType.HISTORY_MESSAGE)
						? RespPacketProto.RespPacket.ImStatus.GET_USER_HISTORY_MESSAGE_SUCCESS
						: RespPacketProto.RespPacket.ImStatus.GET_USER_OFFLINE_MESSAGE_SUCCESS
				: ObjectUtil.isNotEmpty(messageType) && ObjectUtil.equal(messagePacket, MessagePacketProto.MessagePacket.MessageType.HISTORY_MESSAGE)
						? RespPacketProto.RespPacket.ImStatus.GET_USER_HISTORY_MESSAGE_FAILED
						: RespPacketProto.RespPacket.ImStatus.GET_USER_OFFLINE_MESSAGE_FAILED);
		HistoryMessageProto.HistoryMessage historyMessage = HistoryMessageProto.HistoryMessage.newBuilder().setUserId(userId).build();
		switch (messageType) {
			case HISTORY_MESSAGE: break;
			case OFF_LINE_MESSAGE: break;
			default:
				TIMLogUtil.error(log, "错误");
		}
		imChannelContext.getConfig().getProcessor().handleMessagePacket(imChannelContext, messagePacket);
		return getMessageFailedPacket(imChannelContext, ImStatus.C10022);

	}

	private void getHistoryMessage(HistoryMessageProto.HistoryMessage historyMessage, MessagePacketProto.MessagePacket messagePacket) {

	}

	private void getOffLineMessage(HistoryMessageProto.HistoryMessage historyMessage, MessagePacketProto.MessagePacket messagePacket) {

	}


	public ImPacket getMessageFailedPacket(ImChannelContext channelContext, ImStatus status) {
//        RespBody resPacket = new RespBody(Command.COMMAND_GET_MESSAGE_RESP, status);
//        TIM.send(channelContext, new ImPacket(Command.COMMAND_GET_MESSAGE_RESP, resPacket.toByte()));
		return null;
	}
}
