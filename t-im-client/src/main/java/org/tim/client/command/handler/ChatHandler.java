package org.tim.client.command.handler;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.client.TIMClient;
import org.tim.client.TIMClientConfig;
import org.tim.client.command.AbstractCmdHandler;
import org.tim.client.intf.MessageProcessor;
import org.tim.common.ImPacket;
import org.tim.common.packets.ChatBody;
import org.tim.common.packets.Command;
import org.tio.core.ChannelContext;
import org.tio.core.TioConfig;
import org.tio.core.intf.Packet;
import org.tio.utils.lock.MapWithLock;

import java.util.Objects;

/**
 * Created by DELL(mxd) on 2021/12/24 17:26
 */
public class ChatHandler extends AbstractCmdHandler {

    private static final Logger log = LoggerFactory.getLogger(ChatHandler.class);

    @Override
    public Command command() {
        return Command.COMMAND_CHAT_REQ;
    }

    @Override
    public Packet handler(Packet packet, ChannelContext channelContext) {
        ChatBody body = TIMClient.processor.instanceofHandler(packet, ChatBody.class);
        if (ObjectUtil.isEmpty(body)) {
            log.error("消息包格式化出错");
            return null;
        }
        if (Objects.nonNull(body)){
            // 聊天类型int类型(0:未知,1:公聊,2:私聊
            if (body.getChatType() == 0) {
                log.error("用户{}发送未知消息类型", body.getFrom());
                return null;
            }
            if (body.getChatType() == 2) {
                Integer ack = Integer.valueOf(body.getId());
                boolean syn = body.isSyn();
                if (ack > 0) {
                    if (syn) {
                        TIMClient client = (TIMClient) channelContext.get(channelContext.userid);
                        ChatBody build = ChatBody.newBuilder()
                                .from(body.getTo())
                                .to(body.getFrom())
                                .setId(body.getId())
                                .setIsSyn(false)
                                .setCmd(Command.COMMAND_CHAT_RESP.getNumber())
                                .chatType(2)
                                .build();
                        client.send(new ImPacket(Command.COMMAND_CHAT_REQ, build.toByte()));
                    }else {
                        synHandler(channelContext, packet, ack);
                    }
                }
            }
        }

        processor(channelContext).OnMessage(body);

        return null;
    }
}
