package cn.starboot.tim.client;


import cn.starboot.socket.Packet;
import cn.starboot.socket.core.Aio;
import cn.starboot.socket.core.ChannelContext;
import cn.starboot.socket.core.ClientBootstrap;
import cn.starboot.socket.plugins.ReconnectPlugin;
import cn.starboot.tim.client.handler.ImClientPacketProtocolHandler;
import cn.starboot.tim.client.intf.Callback;
import cn.starboot.tim.client.intf.DefaultClientTIMProcessor;
import cn.starboot.tim.client.intf.ClientTIMProcessor;
import cn.starboot.tim.client.protocol.ClientPrivateTcpProtocol;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.ChatPacketProto;
import cn.starboot.tim.common.packet.proto.UserPacketProto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * Created by DELL(mxd) on 2021/12/23 20:33
 */
public class TIMClient {

	//    public static HandlerProcessor processor = new TIMHandlerProcessorImpl();
	public static ThreadPoolExecutor executor;
	//    public static SynThreadPoolExecutor synExecutor;
	private static final Logger log = LoggerFactory.getLogger(TIMClient.class);
	private ChannelContext clientChannelContext;
	//    private static Options options;
	private static TIMClient client;
	//    private static ackSendRunnable ackSendRunnable;
	private UserPacketProto.UserPacket user;
	private final List<String> list = new ArrayList<>();
	private final Map<String, Map<String, String>> map = new HashMap<>();
	private List<Object> extraList;
	private Map<String, Object> extraMap;
	private Object extraObject;
	private Set<Object> extraSet;

	private final ClientBootstrap clientBootstrap;
	private final ImClientConfig imClientConfig;

	// 使用枚举构建单例模式
	private enum TIMClientStarterSingletonEnum {
		INSTANCE;
		private final TIMClient timServerStarter;
		TIMClientStarterSingletonEnum() {
			timServerStarter = new TIMClient(new DefaultClientTIMProcessor());
		}
		private TIMClient getTimServerStarter() {
			return timServerStarter;
		}
	}

	// 对外部提供的获取单例的方法
	public static TIMClient getInstance() {
		return TIMClientStarterSingletonEnum.INSTANCE.getTimServerStarter();
	}

	public TIMClient(ClientTIMProcessor processor) {
		this.clientBootstrap = new ClientBootstrap("127.0.0.1", 8888, ClientPrivateTcpProtocol.getInstance(channelContext -> new ImClientChannelContext(channelContext, getImClientConfig()) ,new ImClientPacketProtocolHandler()));
		this.imClientConfig = new ImClientConfig(processor, clientBootstrap.getConfig());
	}

	public void start() {
		init();

		try {
			clientChannelContext = clientBootstrap.setBufferFactory(10 * 1024 * 1024, 10, true)
					.addPlugin(new ReconnectPlugin(clientBootstrap))
//                    .addHeartPacket()
					.setWriteBufferSize(32 * 1024, 128)
					.setReadBufferSize(32 * 1024)
					.setThreadNum(1)
					.start();
		} catch (IOException e) {
			imClientConfig.getProcessor().connectException(e.getMessage());
		}
	}

	private static void init() {
		// 配置类
	}

	public synchronized void login(String userId, String password, Callback callback) {

//        clientChannelContext.setUserid(userId);
//        clientChannelContext.set(userId, client);
//        LoginReqBody body = new LoginReqBody(userId, password);
//        body.setCreateTime(DateTime.now().getTime());
//        body.setCmd(Command.COMMAND_LOGIN_REQ.getNumber());
//        ImPacket loginPacket = new ImPacket(Command.COMMAND_LOGIN_REQ,body.toByte());
//        ackSend0(loginPacket, 1);
//        clientChannelContext.set("loginCallback", callback);
	}

	public void authReq() {
		// 鉴权
//        AuthReqBody authReqBody = new AuthReqBody();
//        authReqBody.setToken("TIM-token");
//        send(new ImPacket(Command.COMMAND_AUTH_REQ, authReqBody.toByte()));
	}

	/**
	 * 退出
	 */
	public void logout() {
//        CloseReqBody closeReqBody = new CloseReqBody(clientChannelContext.userid);
//        closeReqBody.setCreateTime(DateTime.now().getTime());
//        closeReqBody.setCmd(Command.COMMAND_CLOSE_REQ.getNumber());
//        ImPacket imPacket = new ImPacket(Command.COMMAND_CLOSE_REQ, closeReqBody.toByte());
//        bSend(imPacket);
//        if (!clientChannelContext.getTioConfig().isStopped()) {
//            tioClient.stop();
//            log.info("goodbye.... ");
//        }
	}

	public void messageReq(String userId) {
//        MessageReqBody messageReqBody = new MessageReqBody();
//        messageReqBody.setType(0);
//        messageReqBody.setUserId(clientChannelContext.userid);
//        messageReqBody.setTimelineId(DateTime.now().toString("YYYY-MM-DD"));
//        messageReqBody.setCmd(Command.COMMAND_GET_MESSAGE_REQ.getNumber());
//        messageReqBody.setCreateTime(DateTime.now().getTime());
//        // 获取与指定用户离线消息;
//        send(new ImPacket(Command.COMMAND_GET_MESSAGE_REQ, messageReqBody.toByte()));
//        messageReqBody.setType(1);
//        messageReqBody.setFromUserId(userId);
//        // 获取指定用户历史消息
//        send(new ImPacket(Command.COMMAND_GET_MESSAGE_REQ, messageReqBody.toByte()));
//        messageReqBody.setGroupId("100");
//        // 获取群历史消息
//        send(new ImPacket(Command.COMMAND_GET_MESSAGE_REQ, messageReqBody.toByte()));

	}

	public void onlineUserId() {
//        UserReqBody userReqBody = new UserReqBody();
//        userReqBody.setType(0);
//        userReqBody.setUserId(clientChannelContext.userid);
//        send(new ImPacket(Command.COMMAND_GET_USER_REQ, userReqBody.toByte()));
	}

	public void joinGroup(String groupId) {
//        Group build = Group.newBuilder()
//                .groupId(groupId)
//                .setCmd(Command.COMMAND_JOIN_GROUP_REQ.getNumber())
//                .setCreateTime(DateTime.now().getTime())
//                .build();
//        send(new ImPacket(Command.COMMAND_JOIN_GROUP_REQ, build.toByte()));
	}

	public void sendChatBody(ChatPacketProto.ChatPacket chatBody, Integer ack) {
		if (ack != null) {
//            ackSend(new ImPacket(CommandType.COMMAND_CHAT, chatBody.toByteArray()), ack);
			return;
		}
		send(ImPacket.newBuilder().setTIMCommandType(TIMCommandType.COMMAND_CHAT_REQ).setData(chatBody.toByteArray()).build());
	}

	public void callVideo(String userId) {
		// 视频通话
	}

	public void send(Packet packet) {
		send0(packet);
	}

	public void bSend(Packet packet) {
//        Tio.bSend(clientChannelContext, packet);
	}

	public void ackSend(Packet packet, Integer ack) {
		ackSend0(packet, ack);
	}

	private void send0(Packet packet) {
		setExtraObject(clientChannelContext.isInvalid());
		Aio.send(clientChannelContext, packet);
	}

	private void ackSend0(Packet packet, Integer ack) {

		// 需要ack的消息 同步发送
		if (ack == null || ack <= 0) {
			throw new RuntimeException("synSeq必须大于0");
		}
		// 使用与通讯内核一样强大的同步线程池 哈哈
//        ackSendRunnable.addMsg(new ACKPacket(ack, packet));
		// 发射！！！
//        ackSendRunnable.execute();
	}


	public ImClientConfig getImClientConfig() {
		return imClientConfig;
	}


	public UserPacketProto.UserPacket getUser() {
		return user;
	}

	public void setUser(UserPacketProto.UserPacket user) {
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
