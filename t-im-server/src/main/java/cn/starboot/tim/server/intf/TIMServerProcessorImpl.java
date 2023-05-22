package cn.starboot.tim.server.intf;

import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.ImConfig;
import cn.starboot.tim.common.intf.TIMProcessor;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.*;
import cn.starboot.tim.server.ImServerChannelContext;

public final class TIMServerProcessorImpl implements TIMServerProcessor {


	@Override
	public boolean handleBindPacket(ImServerChannelContext imChannelContext, BindPacketProto.BindPacket bindPacket) {
		return true;
	}

	@Override
	public boolean handleLoginPacket(ImServerChannelContext imChannelContext, LoginPacketProto.LoginPacket loginPacket) {
		return true;
	}

	@Override
	public boolean handleClosePacket(ImServerChannelContext imChannelContext, ClosePacketProto.ClosePacket closePacket) {
		return true;
	}

	@Override
	public boolean handleAuthPacket(ImServerChannelContext imChannelContext, AuthPacketProto.AuthPacket authPacket) {
		return true;
	}

	@Override
	public boolean handleMessagePacket(ImServerChannelContext imChannelContext, MessagePacketProto.MessagePacket messagePacket) {
		return true;
	}

	@Override
	public boolean handleGetUserInfoPacket(ImServerChannelContext imChannelContext, UserInfoPacketProto.UserInfoPacket userInfoPacket) {
		return true;
	}

	@Override
	public UserPacketProto.UserPacket getUserByProcessor(ImServerChannelContext imChannelContext, LoginPacketProto.LoginPacket loginPacket) {
		return null;
	}

	@Override
	public boolean beforeSend(ImChannelContext<? extends ImConfig<? extends TIMProcessor>> imChannelContext, ImPacket packet) {
		System.out.println(packet.toJsonString());
		return true;
	}

	@Override
	public boolean handleChatPacket(ImChannelContext<? extends ImConfig<? extends TIMProcessor>> imChannelContext, ChatPacketProto.ChatPacket chatPacket) {
		System.out.println("ChatReqHandler: 消息内容为 -> " + chatPacket.getContent());
		return true;
	}
}
