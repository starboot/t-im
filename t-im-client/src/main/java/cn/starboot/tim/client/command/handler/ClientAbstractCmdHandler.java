package cn.starboot.tim.client.command.handler;

import cn.starboot.socket.Packet;
import cn.starboot.socket.core.ChannelContext;
import cn.starboot.tim.client.intf.DefaultMessageProcessor;
import cn.starboot.tim.client.intf.MessageProcessor;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.ImConst;
import cn.starboot.tim.common.command.handler.CmdHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by DELL(mxd) on 2021/12/24 16:45
 */
public abstract class ClientAbstractCmdHandler implements CmdHandler, ImConst {

    private static final Logger log = LoggerFactory.getLogger(ClientAbstractCmdHandler.class);

    protected MessageProcessor processor(ImChannelContext channelContext){
//        TioConfig tioConfig = channelContext.getTioConfig();
//        if (tioConfig instanceof TIMClientConfig) {
//            TIMClientConfig clientConfig = (TIMClientConfig) tioConfig;
//            return clientConfig.getProcessor();
//        }
//        log.error("未获取到MessageProcessor实现类");
        return new DefaultMessageProcessor();
    }

    protected void synHandler(ChannelContext channelContext, Packet packet, Integer ack) {
//        MapWithLock<Integer, Packet> waitingResps = channelContext.tioConfig.getWaitingResps();
//        Packet initPacket = waitingResps.remove(ack);
//        if (initPacket != null) {
//            synchronized (initPacket) {
//                waitingResps.put(ack, packet);
//                initPacket.notifyAll();
//            }
//        } else {
//            log.warn("ack:{},被多次确认", ack);
//        }
    }

}
