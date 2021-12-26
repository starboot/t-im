package org.tim.server.helper.redis;

import org.tim.common.packets.ChatBody;
import org.tim.common.packets.Group;
import org.tim.common.packets.User;
import org.tim.common.packets.UserMessageData;
import org.tim.server.cache.TIMCacheHelper;
import org.tio.utils.cache.ICache;
import org.tio.utils.cache.redis.RedisCache;

import java.util.List;

/**
 * 持久到 redis
 * Created by DELL(mxd) on 2021/12/25 21:26
 */
public class RedisTIMCacheHelper implements TIMCacheHelper {

    private static final ICache cache = RedisCache.getCache("TIMServer");

    @Override
    public void writeMessage(String timelineTable, String timelineId, ChatBody chatBody) {
        cache.put(timelineId,chatBody);
    }

    @Override
    public User getUserByType(String userId, Integer type) {
        return null;
    }

    @Override
    public List<User> getAllFriendUsers(String userId, Integer type) {
        return null;
    }

    @Override
    public List<Group> getAllGroupUsers(String userId, Integer type) {
        return null;
    }

    @Override
    public UserMessageData getGroupOfflineMessage(String userId, String groupId) {
        return null;
    }

    @Override
    public UserMessageData getGroupHistoryMessage(String userId, String groupId, Double beginTime, Double endTime, Integer offset, Integer count) {
        return null;
    }

    @Override
    public UserMessageData getFriendsOfflineMessage(String userId) {
        return null;
    }

    @Override
    public UserMessageData getFriendsOfflineMessage(String userId, String fromUserId) {
        return null;
    }

    @Override
    public UserMessageData getFriendHistoryMessage(String userId, String fromUserId, Double beginTime, Double endTime, Integer offset, Integer count) {
        return null;
    }
}
