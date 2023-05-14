package cn.starboot.tim.server.cache;

import cn.starboot.tim.common.packet.proto.ChatPacketProto;
import cn.starboot.tim.common.packet.proto.HistoryMessageProto;

import java.util.Set;

/**
 * 持久化助手
 * Created by DELL(mxd) on 2021/12/25 19:50
 */
public interface TIMPersistentHelper {

    /**
     * 持久化消息
     * @param timelineId 日期
     * @param fromUserId 消息发送用户ID
     * @param toUserId 消息接收用户ID
     * @param chatPacket 消息内容
     * @param isGroup 是否群消息
     * @param isOffline 是否离线消息
     */
    void writeMessage(String timelineId, String fromUserId, String toUserId, ChatPacketProto.ChatPacket chatPacket, boolean isGroup, boolean isOffline);

    /**
     * 获取用户
     * @param type 0:所有在线用户,
     *             1:所有离线线用户(没有意义IM服务器就不做它了),
     *             2:所有用户[在线+离线](获取所有用户更适合用httpAPI访问MySQL,IM服务器没做)
     * @return 用户userID集合
     */
    Set<Long> getUserByType(Integer type);

    /**
     * 获取指定群聊的历史消息
     * @param groupId 群聊ID
     * @param timelineId 日期 2022-01-05
     * @return 历史消息集合
     */
    HistoryMessageProto.HistoryMessage getGroupHistoryMessage(String groupId, String timelineId);

    /**
     * 获取离线消息
     * @param userId 用户ID
     * @param timelineId 日期
     * @return 离线消息集合
     */
	HistoryMessageProto.HistoryMessage getOfflineMessage(String userId, String timelineId);

    /**
     * 获取历史消息
     * @param userId 获取用户ID
     * @param fromUserId 好友ID
     * @param timelineId 获取日期;  如 "2022-01-05"; 如果获取所有则为 "*"
     * @return 历史消息的数据结构
     */
	HistoryMessageProto.HistoryMessage getFriendHistoryMessage(String userId, String fromUserId, String timelineId);
}
