package cn.starboot.tim.server.command.handler;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.packet.CommandType;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.ChatPacketProto;
import cn.starboot.tim.server.command.ServerAbstractCmdHandler;
import cn.starboot.tim.server.protocol.IMServer;
import cn.starboot.tim.server.util.ChatKit;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by DELL(mxd) on 2021/12/24 17:26
 */
public class ChatReqHandler extends ServerAbstractCmdHandler {

    private static final Logger log = LoggerFactory.getLogger(ChatReqHandler.class);

    @Override
    public CommandType command() {
        return CommandType.COMMAND_CHAT;
    }

    @Override
    public ImPacket handler(ImPacket imPacket, ImChannelContext channelContext) throws InvalidProtocolBufferException {
        ChatPacketProto.ChatPacket chatPacket = ChatPacketProto.ChatPacket.parseFrom(imPacket.getData());
        if (ObjectUtil.isEmpty(chatPacket)) {
            log.error("消息包格式化出错");
            return null;
        }
        // 聊天类型int类型(0:未知,1:公聊,2:私聊
        if (chatPacket.getChatType() == ChatPacketProto.ChatPacket.ChatType.GROUP && chatPacket.getGroupId().length() != 0 && !chatPacket.getGroupId().equals("")) {
            if (IMServer.isStore) {
                log.debug("群聊消息--开启了持久化需要将消息保存");
//                IMServer.cacheHelper.writeMessage(DateTime.now().toString("YYYY-MM-DD"), chatPacket.getFromId(), chatPacket.getGroupId(), chatPacket, true, false);
            }
            // 群聊
//            TIM.sendToGroup(TCPSocketServer.getServerTioConfig(), chatPacket.getGroupId(), bytes, context -> {
//                // 不发送自己  true:发送， false：不发送
//                return channelContext != context;
//            }, false);
        }
        if (chatPacket.getChatType() == ChatPacketProto.ChatPacket.ChatType.PRIVATE) {
            String s = DateTime.now().toString("YYYY-MM-DD");
            if (IMServer.isStore) {
                log.debug("私聊消息--开启了持久化需要将消息保存");
//                IMServer.cacheHelper.writeMessage(s,chatPacket.getFromId(), chatPacket.getToId(), chatPacket, false, false);
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