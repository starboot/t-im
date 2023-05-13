package cn.starboot.tim.common.intf;

import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.ChatPacketProto;

/**
 * 用于TIM框架对外暴露的接口
 */
public interface TIMProcessor {

	boolean beforeSend(ImPacket packet);

	boolean handleChatPacket(ChatPacketProto.ChatPacket chatPacket);
}
