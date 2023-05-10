package cn.starboot.tim.common;

import cn.starboot.socket.core.ChannelContext;

public interface ImChannelContextFactory<T extends ImChannelContext> {

	T createImChannelContext(ChannelContext channelContext, ImConfig imConfig);
}
