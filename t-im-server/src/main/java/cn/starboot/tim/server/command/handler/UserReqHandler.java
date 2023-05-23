package cn.starboot.tim.server.command.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.RespPacketProto;
import cn.starboot.tim.common.packet.proto.UserInfoPacketProto;
import cn.starboot.tim.server.ImServerChannelContext;
import cn.starboot.tim.server.command.handler.userInfo.IUserInfo;
import cn.starboot.tim.server.command.handler.userInfo.NonPersistentUserInfo;
import cn.starboot.tim.server.command.handler.userInfo.PersistentUserInfo;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取当前用户下在线好友集合
 *
 * Created by DELL(mxd) on 2021/12/25 16:31
 */
public class UserReqHandler extends AbstractServerCmdHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserReqHandler.class);

	private final IUserInfo persistentUserInfo = new PersistentUserInfo();

	private final IUserInfo nonPersistentUserInfo = new NonPersistentUserInfo();

	@Override
	public TIMCommandType command() {
		return TIMCommandType.COMMAND_USERS_REQ;
	}

	@Override
	public ImPacket handler(ImPacket imPacket, ImServerChannelContext imChannelContext) throws InvalidProtocolBufferException {
		UserInfoPacketProto.UserInfoPacket userInfoPacket = UserInfoPacketProto.UserInfoPacket.parseFrom(imPacket.getData());
		if (ObjectUtil.isEmpty(userInfoPacket)) {
			LOGGER.error("UserReqHandler: message formatting error");
			return null;
		}
		imPacket.setTIMCommandType(TIMCommandType.COMMAND_REQ_RESP);
		if (verify(StrUtil.isNotBlank(userInfoPacket.getUserId()),
				ObjectUtil.isNotNull(userInfoPacket.getStatus()),
				imChannelContext
						.getConfig()
						.getProcessor()
						.handleGetUserInfoPacket(imChannelContext, userInfoPacket))) {
			imPacket.setData(getRespPacket(TIMCommandType.COMMAND_USERS_RESP, RespPacketProto.RespPacket.ImStatus.GET_USER_INFORMATION_SUCCESS, "get user info success").toByteArray());
		} else {
			imPacket.setData(getRespPacket(TIMCommandType.COMMAND_USERS_RESP, RespPacketProto.RespPacket.ImStatus.GET_USER_INFORMATION_FAILED, "get user info failed").toByteArray());
		}
		if (imPacket.getImStatus().equals(RespPacketProto.RespPacket.ImStatus.GET_USER_INFORMATION_FAILED)) {
			return null;
		}
		// 获取用户信息
		if (imChannelContext.getConfig().isStore()) {
			imPacket.setData(persistentUserInfo.getUserInfo(userInfoPacket, imChannelContext).toByteArray());
		} else {
			imPacket.setData(nonPersistentUserInfo.getUserInfo(userInfoPacket, imChannelContext).toByteArray());
		}
		return imChannelContext.getConfig().getProcessor().beforeSend(imChannelContext, imPacket) ? imPacket : null;
	}


}
