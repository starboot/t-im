package cn.starboot.tim.server.intf;

import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.ImConfig;
import cn.starboot.tim.common.intf.TIMProcessor;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.*;
import cn.starboot.tim.server.ImServerChannelContext;
import cn.starboot.tim.server.plugin.AbstractTIMServerProcessorPlugin;

import java.util.ArrayList;
import java.util.List;

public final class TIMServerProcessorAdapter implements TIMServerProcessor {

	private final List<AbstractTIMServerProcessorPlugin> processorList = new ArrayList<>();

	@Override
	public boolean handleBindPacket(ImServerChannelContext imChannelContext, BindPacketProto.BindPacket bindPacket) {
		boolean flag = true;
		for (TIMServerProcessor processor: processorList) {
			flag = flag && processor.handleBindPacket(imChannelContext, bindPacket);
		}
		return flag;
	}

	@Override
	public boolean handleLoginPacket(ImServerChannelContext imChannelContext, LoginPacketProto.LoginPacket loginPacket) {
		boolean flag = true;
		for (TIMServerProcessor processor: processorList) {
			flag = flag && processor.handleLoginPacket(imChannelContext, loginPacket);
		}
		return flag;
	}

	@Override
	public boolean handleClosePacket(ImServerChannelContext imChannelContext, ClosePacketProto.ClosePacket closePacket) {
		boolean flag = true;
		for (TIMServerProcessor processor: processorList) {
			flag = flag && processor.handleClosePacket(imChannelContext, closePacket);
		}
		return flag;
	}

	@Override
	public boolean handleAuthPacket(ImServerChannelContext imChannelContext, AuthPacketProto.AuthPacket authPacket) {
		boolean flag = true;
		for (TIMServerProcessor processor: processorList) {
			flag = flag && processor.handleAuthPacket(imChannelContext, authPacket);
		}
		return flag;
	}

	@Override
	public boolean handleMessagePacket(ImServerChannelContext imChannelContext, MessagePacketProto.MessagePacket messagePacket) {
		boolean flag = true;
		for (TIMServerProcessor processor: processorList) {
			flag = flag && processor.handleMessagePacket(imChannelContext, messagePacket);
		}
		return flag;
	}

	@Override
	public boolean handleGetUserInfoPacket(ImServerChannelContext imChannelContext, UserInfoPacketProto.UserInfoPacket userInfoPacket) {
		boolean flag = true;
		for (TIMServerProcessor processor: processorList) {
			flag = flag && processor.handleGetUserInfoPacket(imChannelContext, userInfoPacket);
		}
		return flag;
	}

	@Override
	public UserPacketProto.UserPacket getUserByProcessor(ImServerChannelContext imChannelContext, LoginPacketProto.LoginPacket loginPacket) {
		boolean flag = true;
		for (TIMServerProcessor processor: processorList) {
			processor.getUserByProcessor(imChannelContext, loginPacket);
		}
		return null;
	}

	@Override
	public boolean beforeSend(ImChannelContext<? extends ImConfig<? extends TIMProcessor>> imChannelContext, ImPacket packet) {
		System.out.println(packet.toJsonString());
		boolean flag = true;
		for (TIMServerProcessor processor: processorList) {
			flag = flag && processor.beforeSend(imChannelContext, packet);
		}
		return flag;
	}

	@Override
	public boolean handleChatPacket(ImChannelContext<? extends ImConfig<? extends TIMProcessor>> imChannelContext, ChatPacketProto.ChatPacket chatPacket) {
		System.out.println("ChatReqHandler: 消息内容为-》" + chatPacket.getContent());
		boolean flag = true;
		for (TIMServerProcessor processor: processorList) {
			flag = flag && processor.handleChatPacket(imChannelContext, chatPacket);
		}
		return flag;
	}

	@Override
	public void addProcessor(AbstractTIMServerProcessorPlugin timServerProcessor) {
		processorList.add(timServerProcessor);
	}
}
