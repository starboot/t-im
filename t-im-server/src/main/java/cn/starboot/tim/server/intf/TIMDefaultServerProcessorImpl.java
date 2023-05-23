package cn.starboot.tim.server.intf;

import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.ImConfig;
import cn.starboot.tim.common.intf.TIMProcessor;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.*;
import cn.starboot.tim.common.util.TIMLogUtil;
import cn.starboot.tim.server.ImServerChannelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 对整个t-im服务端的消息进行监控+二次处理
 * 在这里可以进行控制消息是否转发等一系列
 * 消息持久化也在这里进行处理
 * ;;;;;;;;;;;;;;;;;;;;;
 * 用户可以自行实现 TIMServerProcessor ,便可以进行任意二次开发
 *
 * @author MDong
 */
public final class TIMDefaultServerProcessorImpl implements TIMServerProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(TIMDefaultServerProcessorImpl.class);

	{
		TIMLogUtil.info(LOGGER, "load default server processor: " + LOGGER.getName());
	}

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
