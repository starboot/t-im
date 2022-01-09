package org.tim.client.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.client.TIMClientConfig;
import org.tim.client.intf.DefaultMessageProcessor;
import org.tim.client.intf.MessageProcessor;
import org.tim.common.ImConst;
import org.tim.common.command.CmdHandler;
import org.tio.core.ChannelContext;
import org.tio.core.TioConfig;
import org.tio.core.intf.Packet;
import org.tio.utils.lock.MapWithLock;


/**
 * Created by DELL(mxd) on 2021/12/24 16:45
 */
public abstract class AbstractCmdHandler implements CmdHandler, ImConst {

    private static final Logger log = LoggerFactory.getLogger(AbstractCmdHandler.class);

    protected MessageProcessor processor(ChannelContext channelContext){
        TioConfig tioConfig = channelContext.getTioConfig();
        if (tioConfig instanceof TIMClientConfig) {
            TIMClientConfig clientConfig = (TIMClientConfig) tioConfig;
            return clientConfig.getProcessor();
        }
        log.error("未获取到MessageProcessor实现类");
        return new DefaultMessageProcessor();
    }

    protected void synHandler(ChannelContext channelContext, Packet packet, Integer ack) {
        MapWithLock<Integer, Packet> waitingResps = channelContext.tioConfig.getWaitingResps();
        Packet initPacket = waitingResps.remove(ack);
        if (initPacket != null) {
            synchronized (initPacket) {
                waitingResps.put(ack, packet);
                initPacket.notify();
            }
        } else {
            log.warn("ack:{},被多次确认", ack);
        }
    }

}