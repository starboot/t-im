package cn.starboot.tim.server.command.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.starboot.socket.enums.CloseCode;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.ChatPacketProto;
import cn.starboot.tim.common.packet.proto.RespPacketProto;
import cn.starboot.tim.common.util.TIMLogUtil;
import cn.starboot.tim.server.ImServerChannelContext;
import cn.starboot.tim.server.TIM;
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
	public ImPacket handler(ImPacket imPacket, ImServerChannelContext imChannelContext) throws ImException, InvalidProtocolBufferException {
		ChatPacketProto.ChatPacket chatPacket = ChatPacketProto.ChatPacket.parseFrom(imPacket.getData());
		if (ObjectUtil.isEmpty(chatPacket)) {
			TIMLogUtil.error(LOGGER, "消息包格式化出错");
			TIM.remove(imChannelContext, CloseCode.READ_ERROR);
			return null;
		}
		ImPacket packet = ImPacket.newBuilder()
				.setReq(null)
				.setResp(imPacket.getResp())
				.setTIMCommandType(TIMCommandType.COMMAND_CHAT_RESP)
				.build();
		// 聊天类型类型 (私聊, 群聊, 未知)
		switch (chatPacket.getChatType()) {
			case PRIVATE: {
				if (StrUtil.isNotEmpty(chatPacket.getToId())) {
					// 私聊
					if (ChatKit.isOnline(imChannelContext.getConfig(), chatPacket.getToId())
							&& imChannelContext.getConfig().getProcessor().handleChatPacket(chatPacket)) {
						sendToId(imChannelContext.getConfig(), chatPacket.getToId(), imPacket);
						setSendSuccess(packet);
					}
				}
				break;
			}
			case GROUP: {
				if (StrUtil.isNotEmpty(chatPacket.getGroupId())) {
					// 群聊
					if (imChannelContext.getConfig().getProcessor().handleChatPacket(chatPacket)) {
						sendToGroup(imChannelContext.getConfig(),
								chatPacket.getGroupId(),
								imPacket,
								channelContext -> {
									// 不发送自己  true:发送， false：不发送
									return channelContext != imChannelContext.getChannelContext();
								});
						setSendSuccess(packet);
					}
				}
				break;
			}
			case UNKNOWN: {
				TIMLogUtil.debug(LOGGER, "用户{}发送未知消息类型", chatPacket.getFromId());
				break;
			}
			default: {
				TIMLogUtil.debug(LOGGER, "用户{}发送消息未携带类型", chatPacket.getFromId());
				break;
			}
		}
		return imChannelContext.getConfig().getProcessor().beforeSend(imPacket) ? packet : null;
	}

	private void setSendSuccess(ImPacket packet) {
		packet.setData(RespPacketProto.RespPacket.newBuilder()
				.setImStatus(RespPacketProto.RespPacket.ImStatus.SEND_SUCCESS)
				.build()
				.toByteArray());
	}

	private void setSendFailed(ImPacket packet) {
		packet.setData(RespPacketProto.RespPacket.newBuilder()
				.setImStatus(RespPacketProto.RespPacket.ImStatus.SEND_FAILED)
				.build()
				.toByteArray());
	}
}
