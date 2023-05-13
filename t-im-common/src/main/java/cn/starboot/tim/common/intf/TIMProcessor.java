package cn.starboot.tim.common.intf;

import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.ChatPacketProto;

/**
 * 用于TIM框架对外暴露的接口
 */
public interface TIMProcessor {

	boolean beforeSend(ImChannelContext imChannelContext, ImPacket packet);

	boolean handleChatPacket(ImChannelContext imChannelContext, ChatPacketProto.ChatPacket chatPacket);
}
