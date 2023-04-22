package cn.starboot.tim.server.util;

import cn.hutool.core.collection.CollUtil;
import cn.starboot.socket.core.ChannelContext;
import cn.starboot.tim.common.packets.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by DELL(mxd) on 2021/12/25 19:43
 */
public class ImServerKit {

    private static final Logger log = LoggerFactory.getLogger(ImServerKit.class);

    /**
     * 根据群组获取所有用户;
     * @param groupId 群组ID
     * @return 群组用户集合列表
     */
    public static List<User> getUsersByGroup(String groupId){
//        SetWithLock<ChannelContext> channelContexts = Tio.getByGroup(TCPSocketServer.getServerTioConfig(), groupId);
        List<User> users = new ArrayList<>();
//        if(CollUtil.isEmpty((Iterable<?>) channelContexts)){
//            log.error("群组为：{}的通道上下文为空", groupId);
//        }

        return users;
    }

    /**
     * 根据用户ID获取用户信息(一个用户ID会有多端通道,默认取第一个)
     * @param userId 用户ID
     * @return ChannelContext 通道
     */
    public static ChannelContext getUser(String userId){
//        SetWithLock<ChannelContext> imChannelContexts = Tio.getByUserid(TCPSocketServer.getServerTioConfig(), userId);
//        if(CollUtil.isEmpty((Iterable<?>) imChannelContexts)) {
//            return null;
//        }
//        Set<ChannelContext> obj = imChannelContexts.getObj();
//        Object[] objects = obj.toArray();
//        return (ChannelContext) objects[0];
        return null;
    }

}
