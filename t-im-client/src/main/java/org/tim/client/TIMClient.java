package org.tim.client;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.client.Runnable.ackSendRunnable;
import org.tim.client.intf.*;
import org.tim.common.IMConfig;
import org.tim.common.ImPacket;
import org.tim.common.command.common.HandlerProcessor;
import org.tim.common.command.common.TIMHandlerProcessorImpl;
import org.tim.common.packets.*;
import org.tio.client.ClientChannelContext;
import org.tio.client.ReconnConf;
import org.tio.client.TioClient;
import org.tio.core.Tio;
import org.tio.core.intf.Packet;
import org.tio.utils.Threads;
import org.tio.utils.prop.MapWithLockPropSupport;
import org.tio.utils.thread.pool.SynThreadPoolExecutor;

import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * Created by DELL(mxd) on 2021/12/23 20:33
 */
public class TIMClient extends MapWithLockPropSupport {

    public static HandlerProcessor processor = new TIMHandlerProcessorImpl();
    public static ThreadPoolExecutor executor;
    public static SynThreadPoolExecutor synExecutor;
    private static final Logger log = LoggerFactory.getLogger(TIMClient.class);
    private static ClientChannelContext clientChannelContext;
    private static Options options;
    private static TIMClient client;
    private static TioClient tioClient;
    private static ackSendRunnable ackSendRunnable;
    private User user;
    private final List<String> list = new ArrayList<>();
    private final Map<String, Map<String, String>> map = new HashMap<>();
    private List<Object> extraList;
    private Map<String, Object> extraMap;
    private Object extraObject;
    private Set<Object> extraSet;


    public static void start(Options option) {
        start(option, null);
    }

    public static void start(Options option, MessageProcessor processor) {
        init(option);
        if (ObjectUtil.isEmpty(processor)) {
            processor = new DefaultMessageProcessor();
        }
        TIMClientConfig clientTioConfig = new TIMClientConfig(new ImSocketClientAioHandler(), new ImSocketClientAioListener(), new ReconnConf(2000), processor);
        try {
            tioClient = new TioClient(clientTioConfig);
            clientChannelContext = tioClient.connect(options.getNode());
            ackSendRunnable = new ackSendRunnable(synExecutor, clientChannelContext, option.getTimeout());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void init(Options option) {
        // 配置类
        IMConfig.DEFAULT_CLASSPATH_CONFIGURATION_FILE = "org\\tim\\client\\command\\command.properties";
        if (option.getExecutor() != null) {
            executor = option.getExecutor();
        }else {
            executor = Threads.getGroupExecutor();
        }
        if (option.getSynExecutor() != null) {
            synExecutor = option.getSynExecutor();
        }else {
            synExecutor = Threads.getTioExecutor();
        }
        options = option;
    }

    public synchronized void login(String userId, String password, Callback callback) {

        if (ObjectUtil.isEmpty(client)) {
            client = new TIMClient();
        }
        clientChannelContext.setUserid(userId);
        clientChannelContext.set(userId, client);
        LoginReqBody body = new LoginReqBody(userId, password);
        body.setCreateTime(DateTime.now().getTime());
        body.setCmd(Command.COMMAND_LOGIN_REQ.getNumber());
        ImPacket loginPacket = new ImPacket(Command.COMMAND_LOGIN_REQ,body.toByte());
        ackSend0(loginPacket, 1);
        clientChannelContext.set("loginCallback", callback);
    }

    public void authReq() {
        // 鉴权
        AuthReqBody authReqBody = new AuthReqBody();
        authReqBody.setToken("TIM-token");
        send(new ImPacket(Command.COMMAND_AUTH_REQ, authReqBody.toByte()));
    }

    /**
     * 退出
     */
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

    public void sendChatBody(ChatBody chatBody, Integer ack) {
        if (ack != null) {
            ackSend(new ImPacket(Command.COMMAND_CHAT_REQ, chatBody.toByte()), ack);
            return;
        }
        send(new ImPacket(Command.COMMAND_CHAT_REQ, chatBody.toByte()));
    }

    public void send(Packet packet) {
        send0(packet);
    }

    public void ackSend(Packet packet, Integer ack) {
        ackSend0(packet, ack);
    }

    private void send0(Packet packet) {
        Tio.send(clientChannelContext, packet);
    }

    private void ackSend0(Packet packet, Integer ack) {

        // 需要ack的消息 同步发送
        if (ack == null || ack <= 0) {
            throw new RuntimeException("synSeq必须大于0");
        }
        // 使用与通讯内核一样强大的同步线程池 哈哈
        ackSendRunnable.addMsg(new ACKPacket(ack, packet));
        // 发射！！！
        ackSendRunnable.execute();
    }



    private TIMClient() {
    }

    public static synchronized TIMClient getInstance() {
        if (client == null) {
            client = new TIMClient();
        }
        return client;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public List<Object> getExtraList() {
        return extraList;
    }

    public void setExtraList(List<Object> extraList) {
        this.extraList = extraList;
    }

    public Map<String, Object> getExtraMap() {
        return extraMap;
    }

    public void setExtraMap(Map<String, Object> extraMap) {
        this.extraMap = extraMap;
    }

    public Object getExtraObject() {
        return extraObject;
    }

    public void setExtraObject(Object extraObject) {
        this.extraObject = extraObject;
    }

    public Set<Object> getExtraSet() {
        return extraSet;
    }

    public void setExtraSet(Set<Object> extraSet) {
        this.extraSet = extraSet;
    }

    public List<String> getList() {
        return list;
    }

    public Map<String, Map<String, String>> getMap() {
        return map;
    }
}
