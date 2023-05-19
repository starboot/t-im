package cn.starboot.tim.server.command.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.starboot.socket.enums.CloseCode;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.ChatPacketProto;
import cn.starboot.tim.common.packet.proto.RespPacketProto;
import cn.starboot.tim.common.util.TIMLogUtil;
import cn.starboot.tim.server.ImServerChannelContext;
import cn.starboot.tim.server.TIMServer;
import cn.starboot.tim.server.util.ChatKit;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by DELL(mxd) on 2021/12/24 17:26
 */
public class ChatReqServerHandler extends AbstractServerCmdHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(ChatReqServerHandler.class);

	@Override
	public TIMCommandType command() {
		return TIMCommandType.COMMAND_CHAT_REQ;
	}

	@Override
	public ImPacket handler(ImPacket imPacket, ImServerChannelContext imChannelContext) throws InvalidProtocolBufferException {
		ChatPacketProto.ChatPacket chatPacket = ChatPacketProto.ChatPacket.parseFrom(imPacket.getData());
		if (ObjectUtil.isEmpty(chatPacket)) {
			TIMLogUtil.error(LOGGER, "ChatReqServerHandler: message formatting error");
			TIMServer.close(imChannelContext, CloseCode.READ_ERROR);
			return null;
		}
		ImPacket packet = getImPacket(imChannelContext, TIMCommandType.COMMAND_CHAT_RESP);
		packet.setResp(imPacket.getReq());
		packet.setReq(null);
		// 聊天类型类型 (私聊, 群聊, 未知)
		switch (chatPacket.getChatType()) {
			case PRIVATE: {
				if (StrUtil.isNotEmpty(chatPacket.getToId())) {
					// 私聊
					if (imChannelContext.getConfig().getProcessor().handleChatPacket(imChannelContext, chatPacket)) {
						sendToId(imChannelContext.getConfig(), chatPacket.getToId(), imPacket);
						setRespPacketImStatus(packet, RespPacketProto.RespPacket.ImStatus.SEND_SUCCESS);
					}
				} else {
					setRespPacketImStatus(packet, RespPacketProto.RespPacket.ImStatus.SEND_FAILED);
				}
				break;
			}
			case GROUP: {
				if (StrUtil.isNotEmpty(chatPacket.getGroupId())) {
					// 群聊
					if (imChannelContext.getConfig().getProcessor().handleChatPacket(imChannelContext, chatPacket)) {
						sendToGroup(imChannelContext.getConfig(),
								chatPacket.getGroupId(),
								imPacket,
								channelContext -> {
									// 不发送自己  true:发送， false：不发送
									return channelContext != imChannelContext.getChannelContext();
								});
						setRespPacketImStatus(packet, RespPacketProto.RespPacket.ImStatus.SEND_SUCCESS);
					}
				} else {
					setRespPacketImStatus(packet, RespPacketProto.RespPacket.ImStatus.SEND_FAILED);
				}
				break;
			}
			case UNKNOWN: {
				setRespPacketImStatus(packet, RespPacketProto.RespPacket.ImStatus.SEND_FAILED);
				TIMLogUtil.debug(LOGGER, "client {} send unknown message type", chatPacket.getFromId());
				break;
			}
			default: {
				setRespPacketImStatus(packet, RespPacketProto.RespPacket.ImStatus.SEND_FAILED);
				TIMLogUtil.debug(LOGGER, "client {} packet not exist message type", chatPacket.getFromId());
				break;
			}
		}
		return imChannelContext.getConfig().getProcessor().beforeSend(imChannelContext, packet) ? packet : null;
	}
}
