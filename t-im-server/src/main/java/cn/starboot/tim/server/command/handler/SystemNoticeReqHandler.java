package cn.starboot.tim.server.command.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.SystemNoticePacketProto;
import cn.starboot.tim.server.ImServerChannelContext;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemNoticeReqHandler extends AbstractServerCmdHandler{

	private static final Logger LOGGER = LoggerFactory.getLogger(SystemNoticeReqHandler.class);

	@Override
	public TIMCommandType command() {
		return TIMCommandType.COMMAND_SYSTEM_NOTICE_REQ;
	}

	@Override
	public ImPacket handler(ImPacket imPacket, ImServerChannelContext imChannelContext) throws InvalidProtocolBufferException {
		SystemNoticePacketProto.SystemNoticePacket systemNoticePacket = SystemNoticePacketProto.SystemNoticePacket.parseFrom(imPacket.getData());
		if (ObjectUtil.isEmpty(systemNoticePacket)) {
			LOGGER.error("消息包格式化出错");
			return null;
		}
		ImPacket packet = getImPacket(imChannelContext, TIMCommandType.COMMAND_CHAT_RESP);
		packet.setResp(imPacket.getReq());
		packet.setReq(null);
		switch (systemNoticePacket.getChatType()) {
			case PRIVATE: {
				// 通知到个人
				sendToUser(imChannelContext.getConfig(), systemNoticePacket.getTargetId(), imPacket);
				break;
			}
			case GROUP: {
				// 广播到群
				sendToGroup(imChannelContext.getConfig(), systemNoticePacket.getTargetId(), imPacket);
				break;
			}
		}
		return packet;
	}
}
