package cn.starboot.tim.common.factory;

import cn.starboot.socket.core.ChannelContext;
import cn.starboot.tim.common.ImChannelContext;

public interface ImChannelContextFactory<T extends ImChannelContext<?>> {

	T createImChannelContext(ChannelContext channelContext);
}
