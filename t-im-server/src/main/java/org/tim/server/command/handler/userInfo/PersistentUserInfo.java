package org.tim.server.command.handler.userInfo;



import org.tim.common.packets.User;
import org.tim.common.packets.UserReqBody;
import org.tim.server.cache.TIMCacheHelper;
import org.tim.server.protocol.IMServer;
import org.tio.core.ChannelContext;

import java.util.Objects;

/**
 * 持久化获取用户信息处理
 */
public class PersistentUserInfo implements IUserInfo {

    @Override
    public User getUserInfo(UserReqBody userReqBody, ChannelContext channelContext) {
        String userId = userReqBody.getUserId();
        Integer type = userReqBody.getType();
        //持久化助手;
        TIMCacheHelper cacheHelper = IMServer.cacheHelper;
        User user = cacheHelper.getUserByType(userId, type);
        if(Objects.nonNull(user)) {
            user.setFriends(cacheHelper.getAllFriendUsers(userId, type));
            user.setGroups(cacheHelper.getAllGroupUsers(userId, type));
        }
        return user;
    }

}
