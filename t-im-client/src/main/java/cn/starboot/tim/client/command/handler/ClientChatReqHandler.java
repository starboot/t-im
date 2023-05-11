package cn.starboot.tim.client.command.handler;

import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.ChatPacketProto;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Created by DELL(mxd) on 2021/12/24 17:26
 */
public class ClientChatReqHandler extends AbstractClientCmdHandler {

    private static final Logger log = LoggerFactory.getLogger(ClientChatReqHandler.class);

    @Override
    public TIMCommandType command() {
        return TIMCommandType.COMMAND_CHAT_REQ;
    }

    @Override
    public ImPacket handler(ImPacket imPacket, ImChannelContext channelContext) throws ImException, InvalidProtocolBufferException {
        ChatPacketProto.ChatPacket chatPacket = ChatPacketProto.ChatPacket.parseFrom(imPacket.getData());
        if (Objects.isNull(chatPacket)) {
            throw new ImException("消息包格式化出错");
        }
        // 聊天类型int类型(0:未知,1:公聊,2:私聊
        if (chatPacket.getChatType() == ChatPacketProto.ChatPacket.ChatType.UNKNOWN) {
            log.error("用户{}发送未知消息类型", chatPacket.getFromId());
            return null;
        }
        if (chatPacket.getChatType() == ChatPacketProto.ChatPacket.ChatType.PRIVATE) {
            System.out.println("ChatHandler:私聊消息：" + chatPacket.getContent());
        }

        if (chatPacket.getChatType() == ChatPacketProto.ChatPacket.ChatType.GROUP) {
            System.out.println("ChatHandler:群聊消息：" + chatPacket.getContent());
        }

        processor(channelContext).OnMessage(chatPacket);

        return null;
    }
}
