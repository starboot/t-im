package org.tim.server.stat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.core.TioConfig;
import org.tio.core.intf.Packet;
import org.tio.core.stat.IpStat;
import org.tio.core.stat.IpStatListener;
import org.tio.utils.json.Json;

/**
 * Created by DELL(mxd) on 2021/12/25 10:30
 */
public class TimIpStatListener implements IpStatListener {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(TimIpStatListener.class);

    public static final TimIpStatListener me = new TimIpStatListener();


    private TimIpStatListener() {
    }

    @Override
    public void onExpired(TioConfig tioConfig, IpStat ipStat) {
        //在这里把统计数据入库中或日志
		if (log.isDebugEnabled()) {
			log.debug("可以把统计数据入库\r\n{}", Json.toFormatedJson(ipStat));
		}
    }

    @Override
    public void onAfterConnected(ChannelContext channelContext, boolean isConnected, boolean isReconnect, IpStat ipStat) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("onAfterConnected\r\n{}", Json.toFormatedJson(ipStat));
		}
    }

    @Override
    public void onDecodeError(ChannelContext channelContext, IpStat ipStat) {
		if (log.isDebugEnabled()) {
			log.debug("onDecodeError\r\n{}", Json.toFormatedJson(ipStat));
		}
    }

    @Override
    public void onAfterSent(ChannelContext channelContext, Packet packet, boolean isSentSuccess, IpStat ipStat) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("onAfterSent\r\n{}\r\n{}", packet.logstr(), Json.toFormatedJson(ipStat));
		}
    }

    @Override
    public void onAfterDecoded(ChannelContext channelContext, Packet packet, int packetSize, IpStat ipStat) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("onAfterDecoded\r\n{}\r\n{}", packet.logstr(), Json.toFormatedJson(ipStat));
		}
    }

    @Override
    public void onAfterReceivedBytes(ChannelContext channelContext, int receivedBytes, IpStat ipStat) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("onAfterReceivedBytes\r\n{}", Json.toFormatedJson(ipStat));
		}
    }

    @Override
    public void onAfterHandled(ChannelContext channelContext, Packet packet, IpStat ipStat, long cost) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("onAfterHandled\r\n{}\r\n{}", packet.logstr(), Json.toFormatedJson(ipStat));
		}
    }
}