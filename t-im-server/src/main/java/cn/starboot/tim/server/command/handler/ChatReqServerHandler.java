package cn.starboot.tim.server.command.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.command.ReqCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.ChatPacketProto;
import cn.starboot.tim.server.protocol.IMServer;
import cn.starboot.tim.server.util.ChatKit;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by DELL(mxd) on 2021/12/24 17:26
 */
public class ChatReqServerHandler extends AbstractServerCmdHandler {

    private static final Logger log = LoggerFactory.getLogger(ChatReqServerHandler.class);

    @Override
    public ReqCommandType command() {
        return ReqCommandType.COMMAND_CHAT_REQ;
    }

    @Override
    public ImPacket handler(ImPacket imPacket, ImChannelContext channelContext) throws InvalidProtocolBufferException {
        ChatPacketProto.ChatPacket chatPacket = ChatPacketProto.ChatPacket.parseFrom(imPacket.getData());
        if (ObjectUtil.isEmpty(chatPacket)) {
            log.error("消息包格式化出错");
            return null;
        }
        // 聊天类型类型 (未知, 群聊, 私聊
        if (ObjectUtil.equals(chatPacket.getChatType(), ChatPacketProto.ChatPacket.ChatType.GROUP)
				&& StrUtil.isNotEmpty(chatPacket.getGroupId())) {
            if (IMServer.isStore) {
                log.debug("群聊消息--开启了持久化需要将消息保存");
            }
            // 群聊
            System.out.println(chatPacket.getContent());
//            TIM.sendToGroup(TCPSocketServer.getServerTioConfig(), chatPacket.getGroupId(), bytes, context -> {
//                // 不发送自己  true:发送， false：不发送
//                return channelContext != context;
//            }, false);
        }
        if (ObjectUtil.equals(chatPacket.getChatType(), ChatPacketProto.ChatPacket.ChatType.PRIVATE)
				&& StrUtil.isNotEmpty(chatPacket.getToId())) {
            System.out.println("ChatReqHandler: 消息内容为-》" + chatPacket.getContent());
            send(channelContext, ReqCommandType.COMMAND_CHAT_REQ, chatPacket.toByteArray());
            if (IMServer.isStore) {
                log.debug("私聊消息--开启了持久化需要将消息保存");
            }
            // 私聊
            if (ChatKit.isOnline(chatPacket.getToId()) || IMServer.cluster) {
//                TIM.sendToUser(body, packet);
                return null;
            }
            if (IMServer.isStore) {
                // 做持久化处理
//                IMServer.cacheHelper.writeMessage(s,chatPacket.getFromId(), chatPacket.getToId(), chatPacket, false, true);
            }

        }
        if (chatPacket.getChatType() == ChatPacketProto.ChatPacket.ChatType.UNKNOWN) {
            log.debug("用户{}发送未知消息类型", chatPacket.getFromId());
        }
        return null;
    }
}
