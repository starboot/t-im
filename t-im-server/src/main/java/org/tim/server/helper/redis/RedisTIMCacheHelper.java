package org.tim.server.helper.redis;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.tim.common.packets.ChatBody;
import org.tim.common.packets.UserMessageData;
import org.tim.server.cache.TIMCacheHelper;
import org.tim.server.cluster.ClusterData;
import org.tio.utils.cache.ICache;
import org.tio.utils.cache.redis.RedisCache;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 持久到 redis
 * Created by DELL(mxd) on 2021/12/25 21:26
 */
public class RedisTIMCacheHelper implements TIMCacheHelper {

    private static final ICache cache;

    private static final RedissonClient redissonClient;

    static {
        cache = RedisCache.getCache("TIMCluster");
        redissonClient = RedisCache.getCache("TIMMessage").getRedisson();
    }

    @Override
    public void writeMessage(String timelineId, String fromUserId, String toUserId, ChatBody chatBody, boolean isGroup, boolean isOffline) {

        String key = fromUserId + "-" + toUserId;
        if (isGroup) {
            // 群消息  userId:群ID
            RMap<Object, Object> timGroupMessage = redissonClient.getMap("TIMGroupMessage:" + timelineId);
            saveMessage(toUserId, "TIMGroupMessage", timGroupMessage, chatBody);
            return;
        }
        if (isOffline) {
            // 存储离线消息
            RMap<Object, Object> timOfflineMessage = redissonClient.getMap("TIMOfflineMessage:" + timelineId);
            saveMessage(toUserId, "TIMOfflineMessage:" + timelineId, timOfflineMessage, chatBody);
            return;
        }
        // 存储其他消息
        RMap<Object, Object> timMessage = redissonClient.getMap("TIMMessage:" + timelineId);
        saveMessage(key, "TIMMessage", timMessage, chatBody);

//        Convert.toList(ChatBody.class, timMessage.get(key)).forEach(o -> {
//            ChatBody convert = Convert.convert(ChatBody.class, o);
//            System.out.println(convert.getContent() + "-" + convert.getCreateTime());
//        });
    }

    @Override
    public Set<Long> getUserByType(Integer type) {
        if (type == 0) {
            ClusterData data = cache.get("clusterData", ClusterData.class);
            return data.map.keySet();
        }else {
            return null;
        }
    }

    @Override
    public UserMessageData getGroupHistoryMessage(String groupId, String timelineId) {
        UserMessageData userMessageData = new UserMessageData(groupId);
        Map<String, List<ChatBody>> map = new HashMap<>();
        RMap<Object, Object> timMessage = redissonClient.getMap("TIMGroupMessage:" + timelineId);
        List<ChatBody> chatBodies;
        Object o = timMessage.get(groupId);
        if ("*".equals(timelineId)) {
            // 获取所有时间的历史记录
            System.out.println("获取*的群历史记录");
        }
        if (ObjectUtil.isEmpty(o)) {
            chatBodies = new ArrayList<>();
        }else {
            chatBodies = Convert.toList(ChatBody.class, o);
        }
        map.put(timelineId, chatBodies);
        userMessageData.setFriends(map);
        return userMessageData;
    }

    @Override
    public UserMessageData getOfflineMessage(String userId, String timelineId) {
        UserMessageData userMessageData = new UserMessageData(userId);
        Map<String, List<ChatBody>> map = new HashMap<>();
        RMap<Object, Object> timMessage = redissonClient.getMap("TIMOfflineMessage:" + timelineId);
        List<ChatBody> chatBodies;
        // 取出并删除 分布式服务器加锁处理
        RLock lock = redissonClient.getLock("TIMOfflineMessage:" + timelineId);
        Object o;
        try {
            lock.lock(5L, TimeUnit.SECONDS);
            o = timMessage.remove(userId);
        } finally {
            lock.unlock();
        }
        if (ObjectUtil.isEmpty(o)) {
            chatBodies = new ArrayList<>();
        }else {
            chatBodies = Convert.toList(ChatBody.class, o);
        }
        map.put(userId, chatBodies);
        userMessageData.setFriends(map);
        return userMessageData;
    }


    @Override
    public UserMessageData getFriendHistoryMessage(String userId, String fromUserId, String timelineId) {
        UserMessageData userMessageData = new UserMessageData(userId);
        Map<String, List<ChatBody>> map = new HashMap<>();
        RMap<Object, Object> timMessage = redissonClient.getMap("TIMMessage:" + timelineId);
        String key = userId + "-" + fromUserId;
        String key1 = userId + "-" + fromUserId;
        List<ChatBody> chatBodies;
        Object o = timMessage.get(key);
        if ("*".equals(timelineId)) {
            // 获取所有时间的历史记录
            System.out.println("获取*的历史记录");
        }
        if (ObjectUtil.isEmpty(o)) {
            chatBodies = Convert.toList(ChatBody.class, timMessage.get(key1));
            if (ObjectUtil.isEmpty(chatBodies) || chatBodies == null){
                chatBodies = new ArrayList<>();
            }
        }else {
            chatBodies = Convert.toList(ChatBody.class, o);
        }
        map.put(timelineId, chatBodies);
        userMessageData.setFriends(map);
        return userMessageData;
    }

    /**
     * 加锁处理分布式消息存储
     * @param key 消息主键
     * @param LockName 竞争锁资源名称
     * @param message 缓存中的消息
     * @param chatBody 本次存入的消息体
     */
    private void saveMessage(String key, String LockName, RMap<Object, Object> message, ChatBody chatBody) {
        RLock rLock = redissonClient.getLock(LockName);
        try {
            rLock.lock(5L, TimeUnit.SECONDS);
            if (ObjectUtil.isEmpty(message.get(key))) {
                List<ChatBody> chatBodies = null;
                if (chatBody.getChatType() == 2) {
                    chatBodies = Convert.toList(ChatBody.class, message.get(chatBody.getTo() + "-" + chatBody.getFrom()));
                }
                if (ObjectUtil.isEmpty(chatBodies) || chatBodies == null){
                    chatBodies = new ArrayList<>();
                }
                chatBodies.add(chatBody);
                message.put(key, chatBodies);
            }else {
                List<ChatBody> arrayLists = Convert.toList(ChatBody.class, message.get(key));
                arrayLists.add(chatBody);
                message.put(key, arrayLists);
            }
        } finally {
            rLock.unlock();
        }
    }

}
