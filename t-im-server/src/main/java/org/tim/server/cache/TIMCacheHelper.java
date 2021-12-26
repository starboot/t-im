package org.tim.server.cache;

import org.tim.common.packets.ChatBody;
import org.tim.common.packets.Group;
import org.tim.common.packets.User;
import org.tim.common.packets.UserMessageData;

import java.util.List;

/**
 * Created by DELL(mxd) on 2021/12/25 19:50
 */
public interface TIMCacheHelper {

    void writeMessage(String timelineTable, String timelineId , ChatBody chatBody);

    User getUserByType(String userId, Integer type);

    List<User> getAllFriendUsers(String userId, Integer type);

    List<Group> getAllGroupUsers(String userId, Integer type);

    UserMessageData getGroupOfflineMessage(String userId, String groupId);

    UserMessageData getGroupHistoryMessage(String userId, String groupId, Double beginTime, Double endTime, Integer offset, Integer count);

    UserMessageData getFriendsOfflineMessage(String userId);

    UserMessageData getFriendsOfflineMessage(String userId, String fromUserId);

    UserMessageData getFriendHistoryMessage(String userId, String fromUserId, Double beginTime, Double endTime, Integer offset, Integer count);
}
