package cn.starboot.tim.server;

import cn.starboot.socket.core.ChannelContext;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.ImConfig;

/**
 * 服务器TIM通道上下文
 */
public class ImServerChannelContext extends ImChannelContext {

	public ImServerChannelContext(ChannelContext channelContext, ImConfig config) {
		super(channelContext, config);
	}
}
