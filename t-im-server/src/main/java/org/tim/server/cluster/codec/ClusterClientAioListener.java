package org.tim.server.cluster.codec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.common.ImPacket;
import org.tim.common.packets.Command;
import org.tim.common.packets.LoginReqBody;
import org.tim.server.helper.TIM;
import org.tim.server.protocol.IMServer;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;



/**
 * Created by DELL(mxd) on 2021/12/23 20:29
 */
public class ClusterClientAioListener implements ClientAioListener {


    private static final Logger log = LoggerFactory.getLogger(ClusterClientAioListener.class);

    @Override
    public void onAfterConnected(ChannelContext channelContext, boolean b, boolean b1) {

        log.debug("成功连接一台集群服务器，向其发送登陆信息");
        byte[] loginBody = new LoginReqBody(IMServer.ip + ":" + IMServer.port,"123").toByte();
        ImPacket loginPacket = new ImPacket(Command.COMMAND_LOGIN_REQ,loginBody);
        TIM.send(channelContext, loginPacket);
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
