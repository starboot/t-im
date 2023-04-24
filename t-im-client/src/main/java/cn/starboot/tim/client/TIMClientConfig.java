package cn.starboot.tim.client;


import cn.starboot.socket.core.AioConfig;
import cn.starboot.tim.client.intf.MessageProcessor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by DELL(mxd) on 2022/1/6 20:25
 */
public class TIMClientConfig extends AioConfig {

    private final MessageProcessor processor;

    public TIMClientConfig(boolean isServer, MessageProcessor processor) {
        super(isServer);
        this.processor = processor;
    }

    public MessageProcessor getProcessor() {
        return processor;
    }
}
