package cn.starboot.tim.server;

import cn.starboot.socket.core.ChannelContext;
import cn.starboot.tim.common.ImChannelContext;

import java.util.concurrent.atomic.LongAdder;

/**
 * 服务器TIM通道上下文
 */
public class ImServerChannelContext extends ImChannelContext<ImServerConfig> {

	/**
	 * TIM 服务器配置信息
	 */
	private final ImServerConfig config;

	public ImServerChannelContext(ChannelContext channelContext, ImServerConfig config) {
		super(channelContext, config.getMaximumInterval());
		this.config = config;
	}

	@Override
	public ImServerConfig getConfig() {
		return this.config;
	}
}
