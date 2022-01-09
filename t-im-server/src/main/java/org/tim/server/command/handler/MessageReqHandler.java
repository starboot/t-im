package org.tim.server.command.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.common.ImPacket;
import org.tim.common.ImStatus;
import org.tim.common.packets.Command;
import org.tim.common.packets.MessageReqBody;
import org.tim.common.packets.RespBody;
import org.tim.common.packets.UserMessageData;
import org.tim.server.cache.TIMCacheHelper;
import org.tim.server.command.AbstractCmdHandler;
import org.tim.server.helper.TIM;
import org.tim.server.protocol.IMServer;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.core.intf.Packet;

/**
 * Created by DELL(mxd) on 2021/12/25 16:32
 */
public class MessageReqHandler extends AbstractCmdHandler {

    private static final Logger log = LoggerFactory.getLogger(MessageReqHandler.class);

    @Override
    public Command command() {
        return Command.COMMAND_GET_MESSAGE_REQ;
    }

    @Override
    public Packet handler(Packet packet, ChannelContext channelContext) {

        RespBody resPacket;
        MessageReqBody messageReqBody = IMServer.handlerProcessor.instanceofHandler(packet, MessageReqBody.class);

        if (ObjectUtil.isEmpty(messageReqBody)) {
            log.error("消息包格式化出错");
            return null;
        }
        UserMessageData messageData;
        TIMCacheHelper cacheHelper = IMServer.cacheHelper;
        //群组ID;
        String groupId = messageReqBody.getGroupId();
        //当前用户ID;
        String userId = messageReqBody.getUserId();
        //消息来源用户ID;
        String fromUserId = messageReqBody.getFromUserId();
        //消息类型;
        int type = messageReqBody.getType();
        // 消息日期
        String timelineId = messageReqBody.getTimelineId();
        //如果用户ID为空或者type格式不正确，获取消息失败; 0:离线消息,1:历史消息
        if(StrUtil.isEmpty(userId) || (0 != type && 1 != type)) {
            return getMessageFailedPacket(channelContext, ImStatus.C10015);
        }
        if (!IMServer.isStore) {
            return getMessageFailedPacket(channelContext, ImStatus.C10022);
        }
        if(type == 0){
            resPacket = new RespBody(Command.COMMAND_GET_MESSAGE_RESP,ImStatus.C10016);
        }else{
            resPacket = new RespBody(Command.COMMAND_GET_MESSAGE_RESP,ImStatus.C10018);
        }
        //获取与指定用户离线消息;
        if(0 == type){
            messageData = cacheHelper.getOfflineMessage(userId, timelineId);
            //获取与指定用户历史消息;
        }else {
            // 获取好友历史消息
            if (StrUtil.isEmpty(groupId) && StrUtil.isNotEmpty(fromUserId)) {
                messageData = cacheHelper.getFriendHistoryMessage(userId, fromUserId, timelineId);
            }else {
                // 获取群聊历史消息
                messageData = cacheHelper.getGroupHistoryMessage(groupId, timelineId);
            }
        }
        resPacket.setData(messageData);
        Tio.send(channelContext, new ImPacket(Command.COMMAND_GET_MESSAGE_RESP, resPacket.toByte()));
        return null;
    }



    public ImPacket getMessageFailedPacket(ChannelContext channelContext, ImStatus status) {
        RespBody resPacket = new RespBody(Command.COMMAND_GET_MESSAGE_RESP, status);
        TIM.send(channelContext, new ImPacket(Command.COMMAND_GET_MESSAGE_RESP, resPacket.toByte()));
        return null;
    }
}
