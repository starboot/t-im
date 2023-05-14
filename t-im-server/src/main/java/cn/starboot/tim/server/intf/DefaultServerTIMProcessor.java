package cn.starboot.tim.server.intf;

import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.*;
import cn.starboot.tim.server.ImServerChannelContext;

public class DefaultServerTIMProcessor implements ServerTIMProcessor {

	@Override
	public boolean handleBindPacket(ImServerChannelContext imChannelContext, BindPacketProto.BindPacket bindPacket) {
		return false;
	}

	@Override
	public boolean handleLoginPacket(ImServerChannelContext imChannelContext, LoginPacketProto.LoginPacket loginPacket) {
		return false;
	}

	@Override
	public boolean handleClosePacket(ImServerChannelContext imChannelContext, ClosePacketProto.ClosePacket closePacket) {
		return false;
	}

	@Override
	public boolean handleAuthPacket(ImServerChannelContext imChannelContext, AuthPacketProto.AuthPacket authPacket) {
		return false;
	}

	@Override
	public UserPacketProto.UserPacket getUserByProcessor(ImServerChannelContext imChannelContext, LoginPacketProto.LoginPacket loginPacket) {
		return null;
	}

	@Override
	public boolean beforeSend(ImChannelContext imChannelContext, ImPacket packet) {
		System.out.println(packet.toJsonString());
		return true;
	}

	@Override
	public boolean handleChatPacket(ImChannelContext imChannelContext, ChatPacketProto.ChatPacket chatPacket) {
		System.out.println("ChatReqHandler: 消息内容为-》" + chatPacket.getContent());
		return true;
	}
}
