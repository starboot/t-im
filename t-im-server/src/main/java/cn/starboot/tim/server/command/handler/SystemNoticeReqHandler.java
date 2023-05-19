package cn.starboot.tim.server.command.handler;

import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.SystemNoticePacketProto;
import cn.starboot.tim.server.ImServerChannelContext;
import com.google.protobuf.InvalidProtocolBufferException;

public class SystemNoticeReqHandler extends AbstractServerCmdHandler{

	@Override
	public TIMCommandType command() {
		return TIMCommandType.COMMAND_SYSTEM_NOTICE_REQ;
	}

	@Override
	public ImPacket handler(ImPacket imPacket, ImServerChannelContext imChannelContext) throws ImException, InvalidProtocolBufferException {
		SystemNoticePacketProto.SystemNoticePacket systemNoticePacket = SystemNoticePacketProto.SystemNoticePacket.parseFrom(imPacket.getData());
		if (systemNoticePacket == null) {
			return null;
		}
		switch (systemNoticePacket.getChatType()) {
			case PRIVATE: {
				// 通知到个人
				sendToId(imChannelContext.getConfig(), systemNoticePacket.getTargetId(), imPacket);
				break;
			}
			case GROUP: {
				// 广播到群
				sendToGroup(imChannelContext.getConfig(), systemNoticePacket.getTargetId(), imPacket);
				break;
			}
		}
		return null;
	}
}
