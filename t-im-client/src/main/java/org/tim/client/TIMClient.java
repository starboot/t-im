package org.tim.client;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.client.intf.*;
import org.tim.common.IMConfig;
import org.tim.common.ImPacket;
import org.tim.common.command.common.HandlerProcessor;
import org.tim.common.command.common.TIMHandlerProcessorImpl;
import org.tim.common.packets.*;
import org.tio.client.ClientChannelContext;
import org.tio.client.ReconnConf;
import org.tio.client.TioClient;
import org.tio.core.Node;
import org.tio.core.Tio;
import org.tio.core.intf.Packet;
import org.tio.utils.lock.MapWithLock;


/**
 * Created by DELL(mxd) on 2021/12/23 20:33
 */
public class TIMClient {

    private static final Logger log = LoggerFactory.getLogger(TIMClient.class);
    private static ClientChannelContext clientChannelContext;
    private Options options;
    private static TIMClient client;
    private boolean isLogin = false;
    public static HandlerProcessor processor = new TIMHandlerProcessorImpl();
    private static TioClient tioClient;

    public static void start(Options option) throws Exception {
        start(option, null);
    }

    public static void start(Options option, MessageProcessor processor) throws Exception {
        init(option);
        if (ObjectUtil.isEmpty(processor)) {
            processor = new DefaultMessageProcessor();
        }
        TIMClientConfig clientTioConfig = new TIMClientConfig(new ImSocketClientAioHandler(), new ImSocketClientAioListener(), new ReconnConf(2000), processor);
        tioClient = new TioClient(clientTioConfig);
        clientChannelContext = tioClient.connect(new Node("127.0.0.1", 8888));
    }

    private static void init(Options option) {
        // 配置类
        IMConfig.DEFAULT_CLASSPATH_CONFIGURATION_FILE = "org\\tim\\client\\command\\command.properties";
    }

    public synchronized static void login(String userId, String password, Callback callback) {

        if (ObjectUtil.isEmpty(client)) {
            client = new TIMClient();
        }
        clientChannelContext.setUserid(userId);
        clientChannelContext.set(userId, client);
        LoginReqBody body = new LoginReqBody(userId, password);
        body.setCreateTime(DateTime.now().getTime());
        body.setCmd(Command.COMMAND_LOGIN_REQ.getNumber());
        ImPacket loginPacket = new ImPacket(Command.COMMAND_LOGIN_REQ,body.toByte());
        ackSend0(loginPacket, 5000L, 1);
        callback.func(client.isLogin(), client.isLogin ? client : null);
    }

    public void authReq() {
        // 鉴权
        AuthReqBody authReqBody = new AuthReqBody();
        authReqBody.setToken("TIM-token");
        send(new ImPacket(Command.COMMAND_AUTH_REQ, authReqBody.toByte()));
    }

    public void logout() {
        CloseReqBody closeReqBody = new CloseReqBody(clientChannelContext.userid);
        closeReqBody.setCreateTime(DateTime.now().getTime());
        closeReqBody.setCmd(Command.COMMAND_CLOSE_REQ.getNumber());
        ImPacket imPacket = new ImPacket(Command.COMMAND_CLOSE_REQ, closeReqBody.toByte());
        send(imPacket);
        if (!clientChannelContext.getTioConfig().isStopped()) {
            tioClient.stop();
            log.info("goodbye.... ");
        }
    }
    public void messageReq(String userId) {
        MessageReqBody messageReqBody = new MessageReqBody();
        messageReqBody.setType(0);
        messageReqBody.setUserId(clientChannelContext.userid);
        messageReqBody.setTimelineId(DateTime.now().toString("YYYY-MM-DD"));
        messageReqBody.setCmd(Command.COMMAND_GET_MESSAGE_REQ.getNumber());
        messageReqBody.setCreateTime(DateTime.now().getTime());
        // 获取与指定用户离线消息;
        send(new ImPacket(Command.COMMAND_GET_MESSAGE_REQ, messageReqBody.toByte()));
        messageReqBody.setType(1);
        messageReqBody.setFromUserId(userId);
        // 获取指定用户历史消息
        send(new ImPacket(Command.COMMAND_GET_MESSAGE_REQ, messageReqBody.toByte()));
        messageReqBody.setGroupId("100");
        // 获取群历史消息
        send(new ImPacket(Command.COMMAND_GET_MESSAGE_REQ, messageReqBody.toByte()));

    }

    public void onlineUserId() {
        UserReqBody userReqBody = new UserReqBody();
        userReqBody.setType(0);
        userReqBody.setUserId(clientChannelContext.userid);
        send(new ImPacket(Command.COMMAND_GET_USER_REQ, userReqBody.toByte()));
    }

    public void joinGroup(String groupId) {
        Group build = Group.newBuilder()
                .groupId(groupId)
                .setCmd(Command.COMMAND_JOIN_GROUP_REQ.getNumber())
                .setCreateTime(DateTime.now().getTime())
                .build();
        send(new ImPacket(Command.COMMAND_JOIN_GROUP_REQ, build.toByte()));
    }

    public void sendChatBody(ChatBody chatBody) {
        send(new ImPacket(Command.COMMAND_CHAT_REQ, chatBody.toByte()));
    }

    public void send(Packet packet) {
        send0(packet);
    }



    public void ackSend(Packet packet, long timeout, Integer ack) {
        ackSend0(packet, timeout, ack);
    }


    private static void send0(Packet packet) {
        Tio.send(clientChannelContext, packet);
    }

    private static void ackSend0(Packet packet, long timeout, Integer ack) {
        // 需要ack的消息 同步发送
        if (ack == null || ack <= 0) {
            throw new RuntimeException("synSeq必须大于0");
        }
        MapWithLock<Integer, Packet> waitingResps = clientChannelContext.tioConfig.getWaitingResps();
        try {
            waitingResps.put(ack, packet);
            synchronized (packet) {
                send0(packet);
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
                log.error("respPacket == null,{}", clientChannelContext);
            }
            if (respPacket == packet) {
                log.error("{}, 同步发送超时, {}", clientChannelContext.tioConfig.getName(), clientChannelContext);
            }else {
                log.debug("同步消息ack:{} 同步成功", ack);
            }
        }
    }

    private TIMClient() {
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
