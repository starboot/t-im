package cn.starboot.tim.server.command.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.UsersPacketProto;
import cn.starboot.tim.server.ImServerChannelContext;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取在线用户集合
 * Created by DELL(mxd) on 2021/12/25 16:31
 */
public class UserReqHandler extends AbstractServerCmdHandler {

    private static final Logger log = LoggerFactory.getLogger(UserReqHandler.class);


    @Override
    public TIMCommandType command() {
        return TIMCommandType.COMMAND_USERS_REQ;
    }

	@Override
    public ImPacket handler(ImPacket imPacket, ImServerChannelContext imChannelContext) throws InvalidProtocolBufferException {

        UsersPacketProto.UsersPacket usersPacket = UsersPacketProto.UsersPacket.parseFrom(imPacket.getData());
        if (ObjectUtil.isEmpty(usersPacket)) {
            log.error("消息包格式化出错");
            return null;
        }
//        TIMCacheHelper cacheHelper = IMServer.cacheHelper;
        String userId = usersPacket.getUserId();
        if(usersPacket.getType() == null && StrUtil.isEmpty(userId)) {
//            ImStatusPacketProto.ImStatusPacket.Builder builder1 = ImStatusPacketProto.ImStatusPacket.newBuilder();
//            ImStatusPacketProto.ImStatusPacket build1 = builder1.setStatus(ImStatus.C10004.getCode())
//                    .setDescription(ImStatus.C10004.getDescription())
//                    .setText(ImStatus.C10004.getText())
//                    .build();
            imPacket.setTIMCommandType(TIMCommandType.COMMAND_USERS_REQ)
                    .setData(null);
            // 发送build
//            TIM.send(channelContext, new ImPacket(Command.COMMAND_GET_MESSAGE_RESP, respBody.toByte()));
            return imPacket;
        }
        // 0:所有在线用户,1:所有离线用户,2:所有用户[在线+离线];  服务器只提供查询在线用户，其它查询请用httpAPI
        final UsersPacketProto.UsersPacket.UsersType type = usersPacket.getType();
//        RespBody resPacket = new RespBody(Command.COMMAND_GET_USER_RESP);
        //是否开启持久化;
//        boolean isStore = IMServer.isStore;
//        if(isStore){
//            resPacket.setData(cacheHelper.getUserByType(type));
//        }else {
//            MapWithLock<String, SetWithLock<ChannelContext>> map = channelContext.tioConfig.users.getMap();
//            log.info("在非集群下查询在线人数为:{}", map.size());
//            resPacket.setData(map.size());
//        }
        //在线用户
        if(UsersPacketProto.UsersPacket.UsersType.ONLINE == type){
//            resPacket.setCode(ImStatus.C10005.getCode()).setMsg(ImStatus.C10005.getMsg());
            //离线用户;
        }else if(UsersPacketProto.UsersPacket.UsersType.OFFLINE == type){
//            resPacket.setCode(ImStatus.C10006.getCode()).setMsg(ImStatus.C10006.getMsg());
            //在线+离线用户;
        }else if(UsersPacketProto.UsersPacket.UsersType.ALL == type){
//            resPacket.setCode(ImStatus.C10003.getCode()).setMsg(ImStatus.C10003.getMsg());
        }
        // 将响应包return，框架自动发送
        return null;
    }


}
