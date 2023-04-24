package cn.starboot.tim.common;

import cn.starboot.socket.Packet;
import cn.starboot.socket.core.Aio;
import cn.starboot.socket.core.ChannelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 封装AIO实体类，使得集群发送和单体发送集中管理
 * Created by DELL(mxd) on 2021/12/25 23:21
 */
public class TIM {

    private static final Logger log = LoggerFactory.getLogger(TIM.class);

    public static void send(ChannelContext channelContext, Packet packet) {
        Aio.send(channelContext, packet);
    }


    public static void sendToUser(String to, Packet packet) {

    }
    public static void sendToUser( Packet packet) {

    }

    public static void bindGroup(ChannelContext channelContext, String group) {

    }

    public static void remove(ChannelContext channelContext, String userId) {

    }

    public static void remove(ChannelContext channelContext) {
    }


    public static void sendToGroup() {

    }

    public static void bSend() {
        // 阻塞发送
    }

    public static void ackSend(ChannelContext channelContext,Packet packet, long timeout, Integer ack) {

    }

    private TIM() {
    }

    public static void bindUser(ChannelContext channelContext, String userId) {
        // 绑定用户的时候 顺便把userId绑定在了 channelContext.user id

    }
}
