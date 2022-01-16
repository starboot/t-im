package org.tim.client.Runnable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.client.intf.ACKPacket;
import org.tio.client.ClientChannelContext;
import org.tio.core.Tio;
import org.tio.core.intf.Packet;
import org.tio.utils.lock.MapWithLock;
import org.tio.utils.queue.FullWaitQueue;
import org.tio.utils.queue.TioFullWaitQueue;
import org.tio.utils.thread.pool.AbstractQueueRunnable;
import java.util.concurrent.Executor;

/**
 * 同步消息发送执行器
 * Created by DELL(mxd) on 2022/1/14 14:33
 */
public class ackSendRunnable extends AbstractQueueRunnable<Packet> {

    private static final Logger log = LoggerFactory.getLogger(ackSendRunnable.class);

    private FullWaitQueue<Packet> msgQueue = null;

    private final ClientChannelContext clientChannelContext;

    private final MapWithLock<Integer, Packet> waitingResp;

    private final long timeout;


    public ackSendRunnable(Executor executor, ClientChannelContext client, long timeout) {
        super(executor);
        this.clientChannelContext = client;
        this.waitingResp = client.tioConfig.getWaitingResps();
        this.timeout = timeout;
        this.getMsgQueue();
    }

    public boolean addMsg(Packet t) {
        if (this.isCanceled()) {
            return false;
        } else {
            return this.getMsgQueue().add(t);
        }
    }

    @Override
    public FullWaitQueue<Packet> getMsgQueue() {
        if (this.msgQueue == null) {
            synchronized(this) {
                if (this.msgQueue == null) {
                    this.msgQueue = new TioFullWaitQueue<>(Integer.getInteger("100", null), false);
                }
            }
        }

        return this.msgQueue;
    }

    @Override
    public void runTask() {

        if (msgQueue.isEmpty()) {
            return;
        }
        while (msgQueue.size() > 0) {
            ACKPacket ackPacket = (ACKPacket) msgQueue.poll();
            if (ackPacket == null) {
                continue;
            }
            if (ackPacket.getAck() == null || ackPacket.getAck() <= 0) {
                throw new RuntimeException("synSeq必须大于0");
            }
            try {
                waitingResp.put(ackPacket.getAck(), ackPacket.getPacket());
                synchronized (ackPacket.getPacket()) {
                    Tio.send(clientChannelContext, ackPacket.getPacket());
                    try {
                        ackPacket.getPacket().wait(timeout);
                    } catch (InterruptedException e) {
                        log.error(e.toString(), e);
                    }
                }
            } catch (Throwable e) {
                log.error(e.toString(), e);
            } finally {
                Packet respPacket = waitingResp.remove(ackPacket.getAck());
                if (respPacket == null) {
                    log.error("respPacket == null,{}", clientChannelContext);
                }
                if (respPacket == ackPacket.getPacket()) {
                    log.error("{}, 同步发送超时, {}", clientChannelContext.tioConfig.getName(), clientChannelContext);
                } else {
                    log.debug("同步消息ack:{} 同步成功", ackPacket.getAck());
                }
            }
        }
    }
}
