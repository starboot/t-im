package cn.starboot.tim.common;

import cn.starboot.socket.core.ChannelContext;

/**
 * Created by DELL(mxd) on 2022/9/4 17:02
 */
public class ImChannelContext {

    private final ChannelContext channelContext;

    public ImChannelContext(ChannelContext channelContext) {
        this.channelContext = channelContext;
    }

    public ChannelContext getChannelContext() {
        return channelContext;
    }
}
