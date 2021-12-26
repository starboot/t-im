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
        //消息区间开始时间;
        Double beginTime = messageReqBody.getBeginTime();
        //消息区间结束时间;
        Double endTime = messageReqBody.getEndTime();
        //分页偏移量;
        Integer offset = messageReqBody.getOffset();
        //分页数量;
        Integer count = messageReqBody.getCount();
        //消息类型;
        int type = messageReqBody.getType();
        //如果用户ID为空或者type格式不正确，获取消息失败;
        if(StrUtil.isEmpty(userId) || (0 != type && 1 != type)) {
            return getMessageFailedPacket(channelContext);
        }
        if(type == 0){
            resPacket = new RespBody(Command.COMMAND_GET_MESSAGE_RESP,ImStatus.C10016);
        }else{
            resPacket = new RespBody(Command.COMMAND_GET_MESSAGE_RESP,ImStatus.C10018);
        }
        //群组ID不为空获取用户该群组消息;
        if(!StrUtil.isEmpty(groupId)){
            //离线消息;
            if(0 == type){
                messageData = cacheHelper.getGroupOfflineMessage(userId,groupId);
                //历史消息;
            }else {
                messageData = cacheHelper.getGroupHistoryMessage(userId, groupId,beginTime,endTime,offset,count);
            }
        }else if(StrUtil.isEmpty(fromUserId)){
            //获取用户所有离线消息(好友+群组);
            if(0 == type){
                messageData = cacheHelper.getFriendsOfflineMessage(userId);
            }else{
                return getMessageFailedPacket(channelContext);
            }
        }else{
            //获取与指定用户离线消息;
            if(0 == type){
                messageData = cacheHelper.getFriendsOfflineMessage(userId, fromUserId);
                //获取与指定用户历史消息;
            }else {
                messageData = cacheHelper.getFriendHistoryMessage(userId, fromUserId,beginTime,endTime,offset,count);
            }
        }
        resPacket.setData(messageData);
        Tio.send(channelContext, new ImPacket(Command.COMMAND_GET_MESSAGE_RESP, resPacket.toByte()));
        return null;
    }



    public ImPacket getMessageFailedPacket(ChannelContext channelContext) {
        RespBody resPacket = new RespBody(Command.COMMAND_GET_MESSAGE_RESP, ImStatus.C10015);
        Tio.send(channelContext, new ImPacket(Command.COMMAND_GET_MESSAGE_RESP, resPacket.toByte()));
        return null;
    }
}
