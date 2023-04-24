package cn.starboot.tim.server.command.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.ImStatus;
import cn.starboot.tim.common.command.ReqCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.MessagePacketProto;
import cn.starboot.tim.common.packet.proto.UserMessagePacketProto;
import cn.starboot.tim.server.command.ServerAbstractCmdHandler;
import cn.starboot.tim.server.protocol.IMServer;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL(mxd) on 2021/12/25 16:32
 */
public class MessageReqHandler extends ServerAbstractCmdHandler {

    private static final Logger log = LoggerFactory.getLogger(MessageReqHandler.class);

    @Override
    public ReqCommandType command() {
        return ReqCommandType.COMMAND_MESSAGE_REQ;
    }

    @Override
    public ImPacket handler(ImPacket imPacket, ImChannelContext channelContext) throws InvalidProtocolBufferException {

        MessagePacketProto.MessagePacket messagePacket = MessagePacketProto.MessagePacket.parseFrom(imPacket.getData());
//        final ImPacketProto.ImPacket.Builder imPacket = ImPacketProto.ImPacket.newBuilder();
        if (ObjectUtil.isEmpty(messagePacket)) {
            log.error("消息包格式化出错");
            return null;
        }
        UserMessagePacketProto.UserMessagePacket.Builder builder = UserMessagePacketProto.UserMessagePacket.newBuilder();
        UserMessagePacketProto.UserMessagePacket build = builder.setUserId("1191998028")
                .build();
        List<UserMessagePacketProto.ChatPacket> list= new ArrayList<>();
//        build.getFriendsMap().put("1", list);
//        TIMCacheHelper cacheHelper = IMServer.cacheHelper;
        //群组ID;
        String groupId = messagePacket.getGroupId();
        //当前用户ID;
        String userId = messagePacket.getUserId();
        //消息来源用户ID;
        String fromUserId = messagePacket.getFromUserId();
        //消息类型;
        MessagePacketProto.MessagePacket.MessageType messageType = messagePacket.getMessageType();
        // 消息日期

        //如果用户ID为空或者type格式不正确，获取消息失败; 0:离线消息,1:历史消息
        if(StrUtil.isEmpty(userId)
                || (MessagePacketProto.MessagePacket.MessageType.OFF_LINE_MESSAGE != messageType
                && MessagePacketProto.MessagePacket.MessageType.HISTORY_MESSAGE != messageType)) {
            // 返回失败消息
            return getMessageFailedPacket(channelContext, ImStatus.C10015);
        }
        if (!IMServer.isStore) {
            return getMessageFailedPacket(channelContext, ImStatus.C10022);
        }
        if(MessagePacketProto.MessagePacket.MessageType.OFF_LINE_MESSAGE == messageType){
            // 设置响应包状态为离线消息
//            resPacket = new RespBody(Command.COMMAND_GET_MESSAGE_RESP,ImStatus.C10016);
        } else {
            // 设置响应包状态为历史消息
//            resPacket = new RespBody(Command.COMMAND_GET_MESSAGE_RESP,ImStatus.C10018);
        }
        //获取与指定用户离线消息;
        if(MessagePacketProto.MessagePacket.MessageType.OFF_LINE_MESSAGE == messageType){
//            messageData = cacheHelper.getOfflineMessage(userId, timelineId);
            //获取与指定用户历史消息;
        }else {
            // 获取好友历史消息
            if (StrUtil.isEmpty(groupId) && StrUtil.isNotEmpty(fromUserId)) {
//                messageData = cacheHelper.getFriendHistoryMessage(userId, fromUserId, timelineId);
            }else {
                // 获取群聊历史消息
//                messageData = cacheHelper.getGroupHistoryMessage(groupId, timelineId);
            }
        }
//        resPacket.setData(messageData);
//        Tio.send(channelContext, new ImPacket(Command.COMMAND_GET_MESSAGE_RESP, resPacket.toByte()));
        return null;
    }



    public ImPacket getMessageFailedPacket(ImChannelContext channelContext, ImStatus status) {
//        RespBody resPacket = new RespBody(Command.COMMAND_GET_MESSAGE_RESP, status);
//        TIM.send(channelContext, new ImPacket(Command.COMMAND_GET_MESSAGE_RESP, resPacket.toByte()));
        return null;
    }
}
