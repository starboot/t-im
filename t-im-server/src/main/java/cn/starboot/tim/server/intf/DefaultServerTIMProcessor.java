package cn.starboot.tim.server.intf;

import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.ChatPacketProto;

public class DefaultServerTIMProcessor implements ServerTIMProcessor {
	@Override
	public boolean beforeSend(ImPacket packet) {
		System.out.println(packet.toJsonString());
		return true;
	}

	@Override
	public boolean handleChatPacket(ChatPacketProto.ChatPacket chatPacket) {
		System.out.println("ChatReqHandler: 消息内容为-》" + chatPacket.getContent());
		return true;
	}
}
