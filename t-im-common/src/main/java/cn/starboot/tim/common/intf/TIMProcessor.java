package cn.starboot.tim.common.intf;

import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.ImConfig;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.ChatPacketProto;

/**
 * 用于TIM框架对外暴露的接口
 */
public interface TIMProcessor {

	boolean beforeSend(ImChannelContext<? extends ImConfig<? extends TIMProcessor>> imChannelContext, ImPacket packet);

	boolean handleChatPacket(ImChannelContext<? extends ImConfig<? extends TIMProcessor>> imChannelContext, ChatPacketProto.ChatPacket chatPacket);
}
