package org.tim.server.command.handler.userInfo;

import cn.hutool.core.collection.CollUtil;
import org.tim.common.packets.Group;
import org.tim.common.packets.User;
import org.tim.common.packets.UserReqBody;
import org.tio.core.ChannelContext;

import java.util.ArrayList;
import java.util.List;


/**
 * 非持久化获取用户信息处理
 * Created by DELL(mxd) on 2021/12/25 19:57
 */
public class NonPersistentUserInfo implements IUserInfo {
    /**
     * 好友分组标志
     */
    private static final int FRIEND_GROUP_FLAG = 0;
    /**
     * 群组分组标志
     */
    private static final int GROUP_FLAG = 1;

    @Override
    public User getUserInfo(UserReqBody userReqBody, ChannelContext imChannelContext) {

        return null;
    }

    /**
     * 处理在线用户好友及群组用户;
     * @param groups 用户群组集合
     * @param flag (0:好友,1:群组)
     * @return .
     */
    private static List<Group> initOnlineUserFriendsGroups(List<Group> groups, Integer type, Integer flag){
        if(CollUtil.isEmpty(groups)) {
            return new ArrayList<>();
        }
        //处理好友分组在线用户相关信息;
        return null;
    }

}
