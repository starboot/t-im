package cn.starboot.tim.server.helper.redis;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.starboot.socket.utils.cache.ICache;
import cn.starboot.socket.utils.cache.redis.RedisCache;
import cn.starboot.tim.common.packet.proto.ChatPacketProto;
import cn.starboot.tim.common.packets.UserMessageData;
import cn.starboot.tim.server.cache.TIMCacheHelper;
import cn.starboot.tim.server.cluster.ClusterData;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;

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
    public void writeMessage(String timelineId, String fromUserId, String toUserId, ChatPacketProto.ChatPacket chatPacket, boolean isGroup, boolean isOffline) {

        String key = fromUserId + "-" + toUserId;
        if (isGroup) {
            // 群消息  userId:群ID
            RMap<Object, Object> timGroupMessage = redissonClient.getMap("TIMGroupMessage:" + timelineId);
            saveMessage(toUserId, "TIMGroupMessage", timGroupMessage, chatPacket);
            return;
        }
        if (isOffline) {
            // 存储离线消息
            RMap<Object, Object> timOfflineMessage = redissonClient.getMap("TIMOfflineMessage:" + timelineId);
            saveMessage(toUserId, "TIMOfflineMessage:" + timelineId, timOfflineMessage, chatPacket);
            return;
        }
        // 存储其他消息
        RMap<Object, Object> timMessage = redissonClient.getMap("TIMMessage:" + timelineId);
        saveMessage(key, "TIMMessage", timMessage, chatPacket);

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
        Map<String, List<ChatPacketProto.ChatPacket>> map = new HashMap<>();
        RMap<Object, Object> timMessage = redissonClient.getMap("TIMGroupMessage:" + timelineId);
        List<ChatPacketProto.ChatPacket> chatBodies;
        Object o = timMessage.get(groupId);
        if ("*".equals(timelineId)) {
            // 获取所有时间的历史记录
            System.out.println("获取*的群历史记录");
        }
        if (ObjectUtil.isEmpty(o)) {
            chatBodies = new ArrayList<>();
        }else {
            chatBodies = Convert.toList(ChatPacketProto.ChatPacket.class, o);
        }
        map.put(timelineId, chatBodies);
        userMessageData.setFriends(map);
        return userMessageData;
    }

    @Override
    public UserMessageData getOfflineMessage(String userId, String timelineId) {
        UserMessageData userMessageData = new UserMessageData(userId);
        Map<String, List<ChatPacketProto.ChatPacket>> map = new HashMap<>();
        RMap<Object, Object> timMessage = redissonClient.getMap("TIMOfflineMessage:" + timelineId);
        List<ChatPacketProto.ChatPacket> chatBodies;
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
            chatBodies = Convert.toList(ChatPacketProto.ChatPacket.class, o);
        }
        map.put(userId, chatBodies);
        userMessageData.setFriends(map);
        return userMessageData;
    }


    @Override
    public UserMessageData getFriendHistoryMessage(String userId, String fromUserId, String timelineId) {
        UserMessageData userMessageData = new UserMessageData(userId);
        Map<String, List<ChatPacketProto.ChatPacket>> map = new HashMap<>();
        RMap<Object, Object> timMessage = redissonClient.getMap("TIMMessage:" + timelineId);
        String key = userId + "-" + fromUserId;
        String key1 = userId + "-" + fromUserId;
        List<ChatPacketProto.ChatPacket> chatBodies;
        Object o = timMessage.get(key);
        if ("*".equals(timelineId)) {
            // 获取所有时间的历史记录
            System.out.println("获取*的历史记录");
        }
        if (ObjectUtil.isEmpty(o)) {
            chatBodies = Convert.toList(ChatPacketProto.ChatPacket.class, timMessage.get(key1));
            if (ObjectUtil.isEmpty(chatBodies) || chatBodies == null){
                chatBodies = new ArrayList<>();
            }
        }else {
            chatBodies = Convert.toList(ChatPacketProto.ChatPacket.class, o);
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
     * @param chatPacket 本次存入的消息体
     */
    private void saveMessage(String key, String LockName, RMap<Object, Object> message, ChatPacketProto.ChatPacket chatPacket) {
        RLock rLock = redissonClient.getLock(LockName);
        try {
            rLock.lock(5L, TimeUnit.SECONDS);
            if (ObjectUtil.isEmpty(message.get(key))) {
                List<ChatPacketProto.ChatPacket> chatBodies = null;
                if (chatPacket.getMsgType() == ChatPacketProto.ChatPacket.MsgType.TEXT) {
                    chatBodies = Convert.toList(ChatPacketProto.ChatPacket.class, message.get(chatPacket.getToId() + "-" + chatPacket.getFromId()));
                }
                if (ObjectUtil.isEmpty(chatBodies) || chatBodies == null){
                    chatBodies = new ArrayList<>();
                }
                chatBodies.add(chatPacket);
                message.put(key, chatBodies);
            }else {
                List<ChatPacketProto.ChatPacket> arrayLists = Convert.toList(ChatPacketProto.ChatPacket.class, message.get(key));
                arrayLists.add(chatPacket);
                message.put(key, arrayLists);
            }
        } finally {
            rLock.unlock();
        }
    }

}
