package org.tim.client;

import org.tio.client.intf.ClientAioListener;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;


/**
 * Created by DELL(mxd) on 2021/12/23 20:29
 */
public class ImSocketClientAioListener implements ClientAioListener {



    @Override
    public void onAfterConnected(ChannelContext channelContext, boolean b, boolean b1) {

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
