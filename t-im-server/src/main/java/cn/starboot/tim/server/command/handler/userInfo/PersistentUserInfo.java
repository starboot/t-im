package cn.starboot.tim.server.command.handler.userInfo;

import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.packet.proto.UserInfoPacketProto;
import cn.starboot.tim.common.packet.proto.UserPacketProto;

public class PersistentUserInfo implements IUserInfo{
	@Override
	public UserPacketProto.UserPacket getUserInfo(UserInfoPacketProto.UserInfoPacket userInfoPacket, ImChannelContext imChannelContext) {
		return null;
	}
}
