package cn.starboot.tim.server.command.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.*;
import cn.starboot.tim.common.util.TIMLogUtil;
import cn.starboot.tim.server.ImServerChannelContext;
import cn.starboot.tim.server.TIMServer;
import cn.starboot.tim.server.command.TIMServerCommandManager;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by DELL(mxd) on 2021/12/25 15:01
 */
public class LoginReqHandler extends AbstractServerCmdHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginReqHandler.class);

	@Override
	public TIMCommandType command() {
		return TIMCommandType.COMMAND_LOGIN_REQ;
	}

	@Override
	public ImPacket handler(ImPacket imPacket, ImServerChannelContext imChannelContext) throws InvalidProtocolBufferException {
		LoginPacketProto.LoginPacket loginPacket = LoginPacketProto.LoginPacket.parseFrom(imPacket.getData());
		if (ObjectUtil.isEmpty(loginPacket)) {
			LOGGER.error("消息包格式化出错");
			return null;
		}
		// 构造登录响应消息包
		imPacket.setTIMCommandType(TIMCommandType.COMMAND_LOGIN_RESP);
		setRespPacketImStatus(imPacket,
				verify(StrUtil.isNotBlank(loginPacket.getUserId()),
						StrUtil.isNotBlank(loginPacket.getPassword()),
						StrUtil.isNotBlank(loginPacket.getToken()),
						imChannelContext
								.getConfig()
								.getProcessor()
								.handleLoginPacket(imChannelContext, loginPacket)) ?
						RespPacketProto.RespPacket.ImStatus.LOGIN_SUCCESS :
						RespPacketProto.RespPacket.ImStatus.LOGIN_FAILED);
		UserPacketProto.UserPacket user = imChannelContext.getConfig().getProcessor().getUserByProcessor(imChannelContext, loginPacket);
		if (ObjectUtil.isEmpty(user)) {
			return null;
		}
		// 进行绑定
		TIMServer.bindId(user.getUserId(), imChannelContext);
		//初始化绑定或者解绑群组;
		initGroup(imChannelContext, user);
		return imChannelContext.getConfig().getProcessor().beforeSend(imChannelContext, imPacket.setData(user.toByteArray())) ? imPacket : null;
	}

	/**
	 * 根据用户配置的自定义登录处理器获取业务组装的User信息
	 *
	 * @param imChannelContext 通道上下文
	 * @param loginPacket      登录请求体
	 * @return 用户组装的User信息
	 */
	private UserPacketProto.UserPacket getUserByProcessor(ImServerChannelContext imChannelContext, LoginPacketProto.LoginPacket loginPacket) {
		return UserPacketProto.UserPacket.newBuilder().setUserId(loginPacket.getUserId())
				.setNick("t-im user")
				.setAvatar("")
				.setStatus(TIMEnumProto.TIMEnum.UserStatusType.ONLINE)
				.setSign("让天下没有难开发的即时通讯---米")
				.setTerminal(TIMEnumProto.TIMEnum.Terminal.IOS)
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
	private void initGroup(ImServerChannelContext imChannelContext, UserPacketProto.UserPacket user) {
		List<String> groups = user.getGroupIdList();
		if (groups == null || groups.isEmpty()) {
			return;
		}
		//绑定群组（且绑定状态会返回给客户端）
		for (String group : groups) {
			try {
				TIMServerCommandManager
						.getTIMServerCommandManagerInstance()
						.getCommand(TIMCommandType.COMMAND_BIND_REQ)
						.handler(getImPacket(imChannelContext, TIMCommandType.COMMAND_BIND_REQ,
								RespPacketProto.RespPacket.ImStatus.NONE,
								BindPacketProto
										.BindPacket
										.newBuilder()
										.setBindId(group)
										.setBindType(BindPacketProto.BindPacket.BindType.GROUP)
										.setOptionType(BindPacketProto.BindPacket.OptionType.BIND)
										.build()
										.toByteArray()),
								imChannelContext);
			} catch (Exception e) {
				TIMLogUtil.error(LOGGER, e.getMessage());
			}
		}
	}
}
