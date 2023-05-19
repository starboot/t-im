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

	/**
	 * 消息持久化可以在这里做
	 *
	 * @param imChannelContext 通道上下文
	 * @param chatPacket 消息包
	 * @return 是否处理成功，若返回false则终止发送操作
	 */
	boolean handleChatPacket(ImChannelContext<? extends ImConfig<? extends TIMProcessor>> imChannelContext, ChatPacketProto.ChatPacket chatPacket);


}
