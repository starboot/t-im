package cn.starboot.tim.client;


import cn.starboot.tim.client.intf.MessageProcessor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by DELL(mxd) on 2022/1/6 20:25
 */
public class TIMClientConfig extends ClientTioConfig {

    private final MessageProcessor processor;

    public TIMClientConfig(ClientAioHandler aioHandler, ClientAioListener aioListener, MessageProcessor message) {
        super(aioHandler, aioListener);
        this.processor = message;
    }

    public TIMClientConfig(ClientAioHandler aioHandler, ClientAioListener aioListener, ReconnConf reconnConf, MessageProcessor message) {
        super(aioHandler, aioListener, reconnConf);
        this.processor = message;
        this.name = "t-im";
    }

    public TIMClientConfig(ClientAioHandler aioHandler, ClientAioListener aioListener, ReconnConf reconnConf, SynThreadPoolExecutor tioExecutor, ThreadPoolExecutor groupExecutor, MessageProcessor message) {
        super(aioHandler, aioListener, reconnConf, tioExecutor, groupExecutor);
        this.processor = message;
    }

    public MessageProcessor getProcessor() {
        return processor;
    }
}
