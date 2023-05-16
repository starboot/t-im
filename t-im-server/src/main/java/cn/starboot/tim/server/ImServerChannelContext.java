package cn.starboot.tim.server;

import cn.starboot.socket.core.ChannelContext;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.ImConfig;
import cn.starboot.tim.server.cache.TIMPersistentHelper;
import cn.starboot.tim.server.intf.ServerTIMProcessor;

/**
 * 服务器TIM通道上下文
 */
public class ImServerChannelContext extends ImChannelContext<ImServerConfig> {

	/**
	 * TIM 服务器配置信息
	 */
	private final ImServerConfig config;

	public ImServerChannelContext(ChannelContext channelContext, ImServerConfig config) {
		super(channelContext);
		this.config = config;
	}

	@Override
	public ImServerConfig getConfig() {
		return this.config;
	}
}
