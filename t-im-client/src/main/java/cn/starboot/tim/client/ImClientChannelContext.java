package cn.starboot.tim.client;

import cn.starboot.socket.core.ChannelContext;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.ImConfig;

public class ImClientChannelContext extends ImChannelContext {

	private final ImClientConfig clientConfig;

	public ImClientChannelContext(ChannelContext channelContext, ImClientConfig config) {
		super(channelContext);
		this.clientConfig = config;
	}

	@Override
	public ImConfig getConfig() {
		return this.clientConfig;
	}
}
