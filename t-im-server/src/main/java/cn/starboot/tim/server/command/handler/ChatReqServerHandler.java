package cn.starboot.tim.server.command.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.starboot.socket.enums.CloseCode;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.ImStatus;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.ChatPacketProto;
import cn.starboot.tim.common.packet.proto.RespPacketProto;
import cn.starboot.tim.common.util.TIMLogUtil;
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
	public ImPacket handler(ImPacket imPacket, ImChannelContext imChannelContext) throws ImException, InvalidProtocolBufferException {
		ChatPacketProto.ChatPacket chatPacket = ChatPacketProto.ChatPacket.parseFrom(imPacket.getData());
		if (ObjectUtil.isEmpty(chatPacket)) {
			TIMLogUtil.error(LOGGER, "消息包格式化出错");
			TIM.remove(imChannelContext, CloseCode.READ_ERROR);
			return null;
		}
		final boolean isSyn = ObjectUtil.isNotEmpty(imPacket.getReq());
		final RespPacketProto.RespPacket.Builder respPacketBuilder = RespPacketProto.RespPacket.newBuilder();
		respPacketBuilder.setIsSyn(isSyn);
		// 聊天类型类型 (私聊, 群聊, 未知)
		switch (chatPacket.getChatType()) {
			case PRIVATE: {
				if (StrUtil.isNotEmpty(chatPacket.getToId())) {
					// 私聊
					if (ChatKit.isOnline(imChannelContext.getConfig(), chatPacket.getToId())) {
						System.out.println("ChatReqHandler: 消息内容为-》" + chatPacket.getContent());
						send(imChannelContext, TIMCommandType.COMMAND_CHAT_REQ, imPacket.getData());
						RespPacketProto.RespPacket build = respPacketBuilder.setCode(ImStatus.C10000.getCode()).build();
						return null;
					}
				}
				break;
			}
			case GROUP: {
				if (StrUtil.isNotEmpty(chatPacket.getGroupId())) {
					// 群聊
					System.out.println(chatPacket.getContent());
					sendToGroup(imChannelContext.getConfig(),
							chatPacket.getGroupId(),
							TIMCommandType.COMMAND_CHAT_REQ,
							imPacket.getData(),
							channelContext -> {
								// 不发送自己  true:发送， false：不发送
								return channelContext != imChannelContext.getChannelContext();
							});
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
		return null;
	}
}
