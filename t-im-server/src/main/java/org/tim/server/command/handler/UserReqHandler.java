package org.tim.server.command.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.common.ImPacket;
import org.tim.common.ImStatus;
import org.tim.common.packets.Command;
import org.tim.common.packets.RespBody;
import org.tim.common.packets.UserReqBody;
import org.tim.common.packets.UserStatusType;
import org.tim.server.command.AbstractCmdHandler;
import org.tim.server.command.handler.userInfo.IUserInfo;
import org.tim.server.command.handler.userInfo.NonPersistentUserInfo;
import org.tim.server.command.handler.userInfo.PersistentUserInfo;
import org.tim.server.protocol.IMServer;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.core.intf.Packet;

import java.util.Objects;

/**
 * Created by DELL(mxd) on 2021/12/25 16:31
 */
public class UserReqHandler extends AbstractCmdHandler {

    private static final Logger log = LoggerFactory.getLogger(UserReqHandler.class);
    /**
     * 非持久化用户信息接口
     */
    private final IUserInfo nonPersistentUserInfo;
    /**
     * 持久化用户信息接口
     */
    private final IUserInfo persistentUserInfo;

    public UserReqHandler(){
        persistentUserInfo = new PersistentUserInfo();
        nonPersistentUserInfo = new NonPersistentUserInfo();
    }

    @Override
    public Command command() {
        return Command.COMMAND_GET_USER_REQ;
    }

    @Override
    public Packet handler(Packet packet, ChannelContext channelContext) {

        UserReqBody userReqBody = IMServer.handlerProcessor.instanceofHandler(packet, UserReqBody.class);
        if (ObjectUtil.isEmpty(userReqBody)) {
            log.error("消息包格式化出错");
            return null;
        }
        assert userReqBody != null;
        String userId = userReqBody.getUserId();
        if(StrUtil.isEmpty(userId)) {
            RespBody respBody = new RespBody(Command.COMMAND_GET_USER_RESP, ImStatus.C10004);
            Tio.send(channelContext, new ImPacket(Command.COMMAND_GET_MESSAGE_RESP, respBody.toByte()));
            return null;
        }
        // 0:所有在线用户,1:所有离线用户,2:所有用户[在线+离线]
        int type = userReqBody.getType() == null ? UserStatusType.ALL.getNumber() : userReqBody.getType();
        if(Objects.isNull(UserStatusType.valueOf(type))){
            RespBody respBody = new RespBody(Command.COMMAND_GET_USER_RESP, ImStatus.C10004);
            Tio.send(channelContext, new ImPacket(Command.COMMAND_GET_MESSAGE_RESP, respBody.toByte()));
            return null;
        }
        RespBody resPacket = new RespBody(Command.COMMAND_GET_USER_RESP);
        //是否开启持久化;
        boolean isStore = IMServer.isStore;
        if(isStore){
            resPacket.setData(persistentUserInfo.getUserInfo(userReqBody, channelContext));
        }else {
            resPacket.setData(nonPersistentUserInfo.getUserInfo(userReqBody, channelContext));
        }
        //在线用户
        if(UserStatusType.ONLINE.getNumber() == userReqBody.getType()){
            resPacket.setCode(ImStatus.C10005.getCode()).setMsg(ImStatus.C10005.getMsg());
            //离线用户;
        }else if(UserStatusType.OFFLINE.getNumber() == userReqBody.getType()){
            resPacket.setCode(ImStatus.C10006.getCode()).setMsg(ImStatus.C10006.getMsg());
            //在线+离线用户;
        }else if(UserStatusType.ALL.getNumber() == userReqBody.getType()){
            resPacket.setCode(ImStatus.C10003.getCode()).setMsg(ImStatus.C10003.getMsg());
        }
        Tio.send(channelContext, new ImPacket(Command.COMMAND_GET_USER_RESP, resPacket.toByte()));
        return null;
    }


}
