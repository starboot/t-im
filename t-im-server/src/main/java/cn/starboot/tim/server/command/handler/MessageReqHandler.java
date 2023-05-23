package cn.starboot.tim.server.command.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageReqHandler.class);

	@Override
	public TIMCommandType command() {
		return TIMCommandType.COMMAND_MESSAGE_REQ;
	}

	@Override
	public ImPacket handler(ImPacket imPacket, ImServerChannelContext imChannelContext) throws InvalidProtocolBufferException {
		MessagePacketProto.MessagePacket messagePacket = MessagePacketProto.MessagePacket.parseFrom(imPacket.getData());
		if (ObjectUtil.isEmpty(messagePacket)) {
			TIMLogUtil.error(LOGGER, "MessageReqHandler: message formatting error");
			return null;
		}
		// TIM自研对象重复利用技术
		imPacket.setTIMCommandType(TIMCommandType.COMMAND_MESSAGE_RESP);
		// 群组ID;
		String groupId = messagePacket.getGroupId();
		// 当前用户ID;
		String userId = messagePacket.getUserId();
		// 消息来源用户ID;
		String fromUserId = messagePacket.getFromUserId();
		// 消息类型;
		MessagePacketProto.MessagePacket.MessageType messageType = messagePacket.getMessageType();
		// 消息开始日期
		long beginTime = messagePacket.getBeginTime();
		// 结束日期
		long endTime = messagePacket.getEndTime();
		// 分页偏移量
		int offset = messagePacket.getOffset();
		// 显示消息数量
		int count = messagePacket.getCount();
		if (StrUtil.isBlank(userId) && StrUtil.isNotBlank(imChannelContext.getImChannelContextId())) {
			userId = imChannelContext.getImChannelContextId();
		}
		if (verify(StrUtil.isNotBlank(userId),
				ObjectUtil.isNotNull(messageType),
				ObjectUtil.equal(messagePacket, MessagePacketProto.MessagePacket.MessageType.OFF_LINE_MESSAGE)
						|| (StrUtil.isNotBlank(groupId) || StrUtil.isNotBlank(fromUserId)))) {
			if (ObjectUtil.equal(messagePacket, MessagePacketProto.MessagePacket.MessageType.HISTORY_MESSAGE)) {
				imPacket.setData(getRespPacket(TIMCommandType.COMMAND_MESSAGE_RESP, RespPacketProto.RespPacket.ImStatus.GET_USER_HISTORY_MESSAGE_SUCCESS, "auth success").toByteArray());
			} else {
				imPacket.setData(getRespPacket(TIMCommandType.COMMAND_MESSAGE_RESP, RespPacketProto.RespPacket.ImStatus.GET_USER_OFFLINE_MESSAGE_SUCCESS, "auth success").toByteArray());
			}
		} else {
			if (ObjectUtil.isNotEmpty(messageType) && ObjectUtil.equal(messagePacket, MessagePacketProto.MessagePacket.MessageType.HISTORY_MESSAGE)) {
				imPacket.setData(getRespPacket(TIMCommandType.COMMAND_MESSAGE_RESP, RespPacketProto.RespPacket.ImStatus.GET_USER_HISTORY_MESSAGE_FAILED, "auth failed").toByteArray());
			} else {
				imPacket.setData(getRespPacket(TIMCommandType.COMMAND_MESSAGE_RESP, RespPacketProto.RespPacket.ImStatus.GET_USER_OFFLINE_MESSAGE_FAILED, "auth failed").toByteArray());
			}
		}


		HistoryMessageProto.HistoryMessage.Builder historyMessageBuilder = HistoryMessageProto.HistoryMessage.newBuilder().setUserId(userId);
		switch (messageType) {
			case HISTORY_MESSAGE: {
				imPacket.setData(StrUtil.isNotBlank(groupId) ?
						imChannelContext
								.getConfig()
								.getTimPersistentHelper()
								.getGroupHistoryMessage(groupId, beginTime, endTime, offset, count, historyMessageBuilder)
								.toByteArray() :
						imChannelContext
								.getConfig()
								.getTimPersistentHelper()
								.getFriendHistoryMessage(userId, fromUserId, beginTime, endTime, offset, count, historyMessageBuilder)
								.toByteArray());

			}
			case OFF_LINE_MESSAGE: {
				imPacket.setData(imChannelContext
						.getConfig()
						.getTimPersistentHelper()
						.getOfflineMessage(userId, historyMessageBuilder)
						.toByteArray());
			}
			default:
				TIMLogUtil.error(LOGGER, "unknown select type");
		}
		return imChannelContext.getConfig().getProcessor().beforeSend(imChannelContext, imPacket) ? imPacket : null;
	}
}
