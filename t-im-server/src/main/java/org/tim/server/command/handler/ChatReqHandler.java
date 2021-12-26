package org.tim.server.command.handler;

import cn.hutool.core.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.common.packets.ChatBody;
import org.tim.common.packets.Command;
import org.tim.server.command.AbstractCmdHandler;
import org.tim.server.protocol.IMServer;
import org.tim.server.protocol.tcp.TCPSocketServer;
import org.tim.server.util.ChatKit;
import org.tio.core.ChannelContext;
import org.tio.core.ChannelContextFilter;
import org.tio.core.Tio;
import org.tio.core.intf.Packet;

import java.util.Objects;

/**
 * Created by DELL(mxd) on 2021/12/24 17:26
 */
public class ChatReqHandler extends AbstractCmdHandler {

    private static final Logger log = LoggerFactory.getLogger(ChatReqHandler.class);

    @Override
    public Command command() {
        return Command.COMMAND_CHAT_REQ;
    }

    @Override
    public Packet handler(Packet packet, ChannelContext channelContext) {
        ChatBody body = IMServer.handlerProcessor.instanceofHandler(packet, ChatBody.class);
        if (ObjectUtil.isEmpty(body)) {
            log.error("消息包格式化出错");
            return null;
        }
        if (Objects.nonNull(body)){
            // 聊天类型int类型(0:未知,1:公聊,2:私聊
            if (body.getChatType() == 1 && body.getGroupId().length() != 0 && !body.getGroupId().equals("")) {
                if (IMServer.isStore) {
                    log.debug("群聊消息--开启了持久化需要将消息保存");
                }
                // 群聊
                Tio.sendToGroup(TCPSocketServer.getServerTioConfig(), body.getGroupId(), packet, new ChannelContextFilter() {
                    @Override
                    public boolean filter(ChannelContext context) {
                        // 不发送自己  true:发送， false：不发送
                        return channelContext != context;
                    }
                });
            }
            if (body.getChatType() == 2) {
                if (IMServer.isStore) {
                    log.debug("私聊消息--开启了持久化需要将消息保存");
                }
                // 私聊
                if (ChatKit.isOnline(body.getTo())){
                    Tio.sendToUser(TCPSocketServer.getServerTioConfig(), body.getTo(), packet);
                }else {
                    log.debug("用户->{}:不在线",body.getTo());
                }
            }
            if (body.getChatType() == 0) {
                log.debug("用户{}发送未知消息类型", body.getFrom());
            }
        }
        return null;
    }
}
