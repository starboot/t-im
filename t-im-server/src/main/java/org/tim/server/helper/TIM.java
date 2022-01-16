package org.tim.server.helper;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.common.ImPacket;
import org.tim.common.packets.ChatBody;
import org.tim.common.packets.Command;
import org.tim.server.cluster.ClusterData;
import org.tim.server.protocol.IMServer;
import org.tim.server.protocol.tcp.TCPSocketServer;
import org.tio.core.ChannelContext;
import org.tio.core.ChannelContextFilter;
import org.tio.core.Tio;
import org.tio.core.TioConfig;
import org.tio.core.intf.Packet;
import org.tio.server.ServerTioConfig;
import org.tio.utils.lock.MapWithLock;
import org.tio.utils.lock.SetWithLock;



/**
 * 封装tio，使得集群发送和单体发送集中管理
 * Created by DELL(mxd) on 2021/12/25 23:21
 */
public class TIM {

    private static final Logger log = LoggerFactory.getLogger(TIM.class);

    public static void send(ChannelContext channelContext, Packet packet) {
        Tio.send(channelContext, packet);
    }


    public static void sendToUser(String to, Packet packet) {
        ServerTioConfig tioConfig = TCPSocketServer.getServerTioConfig();
        SetWithLock<ChannelContext> byUserId = Tio.getByUserid(tioConfig, to);
        if (ObjectUtil.isNotEmpty(byUserId) && byUserId.size() > 0) {
            Tio.sendToUser(tioConfig, to, packet);
        }else {
            // 用户不在线或者在其他集群服务器
            log.error("集群发来的消息 接收方不在本服务器，或者已经离线");
        }
    }
    public static void sendToUser(ChatBody body, Packet packet) {
        ServerTioConfig tioConfig = TCPSocketServer.getServerTioConfig();
        SetWithLock<ChannelContext> byUserId = Tio.getByUserid(tioConfig, body.getTo());
        if (ObjectUtil.isNotEmpty(byUserId) && byUserId.size() > 0) {
            Tio.sendToUser(tioConfig, body.getTo(), packet);
        }else {
            // 用户不在线或者在其他集群服务器
            log.info("用户->{}:不在线,通过集群发送", body.getTo());
            // 1. 在缓存中央查询用户数否在线
            ClusterData cluster = IMServer.clusterHelper.getCluster();
            String TIMServerID = cluster.map.get(Long.decode(body.getTo()));
            if (StrUtil.isEmpty(TIMServerID)) {
                // 用户离线, 将消息离线缓存
                log.debug("将消息持久化为离线消息");
                IMServer.cacheHelper.writeMessage(DateTime.now().toString("YYYY-MM-DD"),
                        body.getFrom(), body.getTo(), body,
                        false, true);
                ChatBody build = ChatBody.newBuilder()
                        .setId(body.getId())
                        .setIsSyn(false)
                        .setCmd(Command.COMMAND_CHAT_REQ.getNumber())
                        .build();
                ImPacket imPacket = new ImPacket(Command.COMMAND_CHAT_REQ, build.toByte());
                Tio.sendToUser(tioConfig, body.getTo(), imPacket);

            }else {
                // 发送到远程集群服务器
                Tio.sendToUser(tioConfig, TIMServerID, packet);
            }
        }
    }

    public static void bindGroup(ChannelContext channelContext, String group) {
        // 阻塞发送
        Tio.bindGroup(channelContext, group);
    }

    public static void remove(ChannelContext channelContext, String userId) {
        if (channelContext.userid.equals(userId)) {
            Tio.remove(channelContext, "收到关闭连接请求");
        }else {
            log.error("用户非法关闭他人连接");
        }
    }

    public static void remove(ChannelContext channelContext) {
        Tio.remove(channelContext, "用户发送异常关闭请求，将其强行断开");
    }

    /**
     * 向群组中发送
     * @param tioConfig tioConfig
     * @param group 群ID
     * @param packet 消息包
     * @param channelContextFilter 过滤器
     * @param isCluster 是否是集群发来的  否：将向集群中发送，否则不再发向集群了
     */
    public static void sendToGroup(TioConfig tioConfig, String group, Packet packet, ChannelContextFilter channelContextFilter, boolean isCluster) {
        Tio.sendToGroup(tioConfig, group, packet, channelContextFilter);
        if (!isCluster && IMServer.cluster) {
            Tio.sendToGroup(tioConfig, "clusterGroup", packet, channelContextFilter);
        }
    }

    public static void bSend() {
        // 阻塞发送
    }

    public static void ackSend(ChannelContext channelContext,Packet packet, long timeout, Integer ack) {
        // 需要ack的消息 同步发送
        if (ack == null || ack <= 0) {
            throw new RuntimeException("synSeq必须大于0");
        }

        MapWithLock<Integer, Packet> waitingResps = channelContext.tioConfig.getWaitingResps();
        try {
            waitingResps.put(ack, packet);

            synchronized (packet) {
                send(channelContext, packet);
                try {
                    packet.wait(timeout);
                } catch (InterruptedException e) {
                    log.error(e.toString(), e);
                }
            }
        } catch (Throwable e) {
            log.error(e.toString(), e);
        } finally {
            Packet respPacket = waitingResps.remove(ack);
            if (respPacket == null) {
                log.error("respPacket == null,{}", channelContext);
            }
            if (respPacket == packet) {
                log.error("{}, 同步发送超时, {}", channelContext.tioConfig.getName(), channelContext);
            }else {
                log.debug("同步消息ack:{} 同步成功", ack);
            }
        }
    }

    private TIM() {
    }

    public static void bindUser(ChannelContext channelContext, String userId) {
        // 绑定用户的时候 顺便把userId绑定在了 channelContext.user id
        Tio.bindUser(channelContext, userId);
    }
}
