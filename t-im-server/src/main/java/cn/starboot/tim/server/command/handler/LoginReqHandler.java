package cn.starboot.tim.server.command.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.LoginPacketProto;
import cn.starboot.tim.common.packet.proto.RespPacketProto;
import cn.starboot.tim.common.packet.proto.UserPacketProto;
import cn.starboot.tim.server.ImServerChannelContext;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by DELL(mxd) on 2021/12/25 15:01
 */
public class LoginReqHandler extends AbstractServerCmdHandler {
	private static final Logger log = LoggerFactory.getLogger(LoginReqHandler.class);

	@Override
	public TIMCommandType command() {
		return TIMCommandType.COMMAND_LOGIN_REQ;
	}

	@Override
	public ImPacket handler(ImPacket imPacket, ImServerChannelContext imChannelContext) throws InvalidProtocolBufferException {
		LoginPacketProto.LoginPacket loginPacket = LoginPacketProto.LoginPacket.parseFrom(imPacket.getData());
		if (ObjectUtil.isEmpty(loginPacket)) {
			log.error("消息包格式化出错");
			return null;
		}
		// 构造登录响应消息包
		ImPacket build = ImPacket.newBuilder().setTIMCommandType(TIMCommandType.COMMAND_LOGIN_RESP).build();
		setRespPacketImStatus(build,
				verify(StrUtil.isNotBlank(loginPacket.getUserId()),
						StrUtil.isNotBlank(loginPacket.getPassword()),
						StrUtil.isNotBlank(loginPacket.getToken()),
						imChannelContext.getConfig().getProcessor().handleLoginPacket(imChannelContext, loginPacket)
				) ? RespPacketProto.RespPacket.ImStatus.LOGIN_SUCCESS : RespPacketProto.RespPacket.ImStatus.LOGIN_FAILED);

		UserPacketProto.UserPacket user = imChannelContext.getConfig().getProcessor().getUserByProcessor(imChannelContext, loginPacket);
		if (ObjectUtil.isNotEmpty(user)) {
			if (StrUtil.isNotBlank(user.getUserId())) {
//				user.setUserId(loginPacket.getUserId());
			}
//            TIM.bindUser(channelContext, user.getUserId());
			//初始化绑定或者解绑群组;
			initGroup(imChannelContext, user);
//            LoginRespBody respBody = new LoginRespBody(ImStatus.C10007, user, "t-im token");
//            respBody.setId("1");
//            respBody.setSyn(false);
//            Tio.send(channelContext, new ImPacket(Command.COMMAND_LOGIN_RESP, respBody.toByte()));
			// 用户注册进集群
			if (user.getUserId().contains(":")) {
				// 该登录用户为服务器 不注册进集群
//                TIM.bindGroup(channelContext, "clusterGroup");
				return null;
			}
//            if (IMServer.cluster && IMServer.isStore) {
			// 将用户注册进集群中间件
//                IMServer.clusterHelper.pushCluster(Long.decode(user.getUserId()),
//                        IMServer.ip + ":" + IMServer.port);
//            }

		}

		return build;
	}

	/**
	 * 根据用户配置的自定义登录处理器获取业务组装的User信息
	 *
	 * @param imChannelContext 通道上下文
	 * @param loginPacket      登录请求体
	 *                         //     * @param loginRespBody 登录响应体
	 * @return 用户组装的User信息
	 */
	private UserPacketProto.UserPacket getUserByProcessor(ImChannelContext imChannelContext, LoginPacketProto.LoginPacket loginPacket) {
//        if (ObjectUtil.isEmpty(loginRespBody) || loginRespBody.getStatus() != ImStatus.C10007.getCode()) {
//            log.error("login failed, userId:{}, password:{}", loginReqBody.getUserId(), loginReqBody.getToken());
//            return null;
//        }
		return UserPacketProto.UserPacket.newBuilder().setUserId(loginPacket.getUserId())
				.setNick("t-im user")
				.setAvatar("")
				.setStatus(UserPacketProto.UserPacket.UsersType.ONLINE)
				.setSign("让天下没有难开发的即时通讯---米")
				.setTerminal(UserPacketProto.UserPacket.Terminal.IOS)
				.addFriends(UserPacketProto.UserPacket.newBuilder().setUserId("")
						.setAvatar(""))
				.addGroupId("111")
				.addGroupId("222")
				.setPinyin("")
				.setFirstLetter("")
				.build();
	}

	/**
	 * 初始化绑定或者解绑群组;
	 */
	public void initGroup(ImChannelContext imChannelContext, UserPacketProto.UserPacket user) {
		String userId = user.getUserId();
		List<String> groups = user.getGroupIdList();
		if (groups == null || groups.isEmpty()) {
			return;
		}
//        boolean isStore = TCPSocketServer.isStore;
		List<String> groupIds = null;
//        if(isStore){
//            log.debug("从存储介质中读取groupIds");
//        }
		//绑定群组
		for (String group : groups) {
//            ImPacket groupPacket = new ImPacket(Command.COMMAND_JOIN_GROUP_REQ, JsonKit.toJsonBytes(group));
			try {
//                BindReqHandler bindReqHandler = TIMServerCommandManager.getCommand(TIMCommandType.COMMAND_BIND_REQ, BindReqHandler.class);
//                if (bindReqHandler != null) {
//                    joinGroupReqHandler.handler(groupPacket, imChannelContext);
//                }
			} catch (Exception e) {
				log.error("JoinGroupReqHandler is not contain");
			}
		}
	}
}
