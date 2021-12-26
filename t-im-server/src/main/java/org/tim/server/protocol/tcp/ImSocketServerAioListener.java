package org.tim.server.protocol.tcp;

import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.core.intf.Packet;
import org.tio.server.intf.ServerAioListener;


/**
 * Created by DELL(mxd) on 2021/12/23 20:29
 */
public class ImSocketServerAioListener implements ServerAioListener {
    @Override
    public boolean onHeartbeatTimeout(ChannelContext channelContext, Long aLong, int i) {
        return false;
    }

    @Override
    public void onAfterConnected(ChannelContext channelContext, boolean b, boolean b1) {

        Tio.bindUser(channelContext, "Socket");

        Tio.bindGroup(channelContext, "100");

    }

    @Override
    public void onAfterDecoded(ChannelContext channelContext, Packet packet, int i) {

    }

    @Override
    public void onAfterReceivedBytes(ChannelContext channelContext, int i) {

    }

    @Override
    public void onAfterSent(ChannelContext channelContext, Packet packet, boolean b) {

    }

    @Override
    public void onAfterHandled(ChannelContext channelContext, Packet packet, long l) {

    }

    @Override
    public void onBeforeClose(ChannelContext channelContext, Throwable throwable, String s, boolean b) {

    }
}
