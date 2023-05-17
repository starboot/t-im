package cn.starboot.tim.client.command.handler;

//import cn.starboot.socket.ChannelContextFilter;
//import cn.starboot.socket.Packet;
//import cn.starboot.socket.core.ChannelContext;
import cn.starboot.tim.client.ImClientChannelContext;
import cn.starboot.tim.client.ImClientConfig;
import cn.starboot.tim.client.intf.DefaultClientTIMProcessor;
import cn.starboot.tim.client.intf.ClientTIMProcessor;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.command.handler.AbstractCmdHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by DELL(mxd) on 2021/12/24 16:45
 */
public abstract class AbstractClientCmdHandler extends AbstractCmdHandler<ImClientChannelContext, ImClientConfig, ClientTIMProcessor> {

    private static final Logger log = LoggerFactory.getLogger(AbstractClientCmdHandler.class);


	protected void send(ImChannelContext<ImClientConfig> imChannelContext, TIMCommandType TIMCommandType, byte[] data) {

	}

	protected ClientTIMProcessor processor(ImChannelContext<ImClientConfig> channelContext){
//        TioConfig tioConfig = channelContext.getTioConfig();
//        if (tioConfig instanceof TIMClientConfig) {
//            TIMClientConfig clientConfig = (TIMClientConfig) tioConfig;
//            return clientConfig.getProcessor();
//        }
//        log.error("未获取到MessageProcessor实现类");
        return new DefaultClientTIMProcessor();
    }

//    protected void synHandler(ChannelContext channelContext, Packet packet, Integer ack) {
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
//    }

}
