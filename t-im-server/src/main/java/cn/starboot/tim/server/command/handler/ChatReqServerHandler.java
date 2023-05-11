package cn.starboot.tim.server.command.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.command.ReqServerCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.ChatPacketProto;
import cn.starboot.tim.common.util.TIMLogUtil;
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
    public ReqServerCommandType command() {
        return ReqServerCommandType.COMMAND_CHAT_REQ;
    }

    @Override
    public ImPacket handler(ImPacket imPacket, ImChannelContext channelContext) throws InvalidProtocolBufferException {
        ChatPacketProto.ChatPacket chatPacket = ChatPacketProto.ChatPacket.parseFrom(imPacket.getData());
        if (ObjectUtil.isEmpty(chatPacket)) {
			TIMLogUtil.error(LOGGER, "消息包格式化出错");
            return null;
        }
		// 聊天类型类型 (私聊, 群聊, 未知)
        switch (chatPacket.getChatType()) {
			case PRIVATE: {
				if (StrUtil.isNotEmpty(chatPacket.getToId())) {
					System.out.println("ChatReqHandler: 消息内容为-》" + chatPacket.getContent());
					send(channelContext, ReqServerCommandType.COMMAND_CHAT_REQ, chatPacket.toByteArray());
					// 私聊
					if (ChatKit.isOnline(chatPacket.getToId())) {
//                TIM.sendToUser(body, packet);
						return null;
					}
				}
				break;
			}
			case GROUP: {
				if (StrUtil.isNotEmpty(chatPacket.getGroupId())) {
//					if (IMServer.isStore) {
//						TIMLogUtil.debug(LOGGER, "群聊消息--开启了持久化需要将消息保存");
//					}
					// 群聊
					System.out.println(chatPacket.getContent());
//            TIM.sendToGroup(TCPSocketServer.getServerTioConfig(), chatPacket.getGroupId(), bytes, context -> {
//                // 不发送自己  true:发送， false：不发送
//                return channelContext != context;
//            }, false);
				}
				break;
			}
			case UNKNOWN: {
				TIMLogUtil.debug(LOGGER, "用户{}发送未知消息类型", chatPacket.getFromId());
				break;
			}
			default: {
				TIMLogUtil.debug(LOGGER, "用户{}发送错误消息", chatPacket.getFromId());
				break;
			}
		}
        return null;
    }
}
