package org.tim.server.command.handler;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.common.packets.ChatBody;
import org.tim.common.packets.Command;
import org.tim.server.command.AbstractCmdHandler;
import org.tim.server.helper.TIM;
import org.tim.server.protocol.IMServer;
import org.tim.server.protocol.tcp.TCPSocketServer;
import org.tim.server.util.ChatKit;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;

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
        if (ObjectUtil.isNotEmpty(body)){
            // 聊天类型int类型(0:未知,1:公聊,2:私聊
            if (body.getChatType() == 1 && body.getGroupId().length() != 0 && !body.getGroupId().equals("")) {
                if (IMServer.isStore) {
                    log.debug("群聊消息--开启了持久化需要将消息保存");
                    IMServer.cacheHelper.writeMessage(DateTime.now().toString("YYYY-MM-DD"), body.getFrom(), body.getGroupId(), body, true, false);
                }
                // 群聊
                TIM.sendToGroup(TCPSocketServer.getServerTioConfig(), body.getGroupId(), packet, context -> {
                    // 不发送自己  true:发送， false：不发送
                    return channelContext != context;
                }, false);
            }
            if (body.getChatType() == 2) {
                String s = DateTime.now().toString("YYYY-MM-DD");
                if (IMServer.isStore) {
                    log.debug("私聊消息--开启了持久化需要将消息保存");
                    IMServer.cacheHelper.writeMessage(s,body.getFrom(), body.getTo(), body, false, false);
                }
                // 私聊
                if (ChatKit.isOnline(body.getTo()) || IMServer.cluster) {
                    TIM.sendToUser(body, packet);
                    return null;
                }
                if (IMServer.isStore) {
                    // 做持久化处理
                    IMServer.cacheHelper.writeMessage(s,body.getFrom(), body.getTo(), body, false, true);
                }

            }
            if (body.getChatType() == 0) {
                log.debug("用户{}发送未知消息类型", body.getFrom());
            }
        }
        return null;
    }
}
