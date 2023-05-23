package cn.starboot.tim.client.command.handler;

import cn.starboot.tim.client.ImClientChannelContext;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.SystemNoticePacketProto;
import com.google.protobuf.InvalidProtocolBufferException;

public class SystemNoticeReqHandler extends AbstractClientCmdHandler {
	@Override
	public TIMCommandType command() {
		return TIMCommandType.COMMAND_SYSTEM_NOTICE_REQ;
	}

	@Override
	public ImPacket handler(ImPacket imPacket, ImClientChannelContext imChannelContext) throws ImException, InvalidProtocolBufferException {
		SystemNoticePacketProto.SystemNoticePacket systemNoticePacket = SystemNoticePacketProto.SystemNoticePacket.parseFrom(imPacket.getData());
		if (systemNoticePacket == null) {
			return null;
		}
		switch (systemNoticePacket.getChatType()) {
			case PRIVATE: handlePrivate(systemNoticePacket, imChannelContext); break;
			case GROUP: handleGroup(systemNoticePacket, imChannelContext); break;
		}
		return null;
	}

	private void handlePrivate(SystemNoticePacketProto.SystemNoticePacket systemNoticePacket, ImClientChannelContext imChannelContext) {
		switch (systemNoticePacket.getSystemNoticeType()) {
			case ADD_FRIEND_REQ: break;
			case ADD_FRIEND_RESP: break;
		}
	}

	private void handleGroup(SystemNoticePacketProto.SystemNoticePacket systemNoticePacket, ImClientChannelContext imChannelContext) {
		switch (systemNoticePacket.getSystemNoticeType()) {
			case ADD_GROUP_REQ: break;
			case ADD_GROUP_RESP: break;
		}
	}
}
