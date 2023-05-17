package cn.starboot.tim.client;

import cn.starboot.socket.core.AioConfig;
import cn.starboot.tim.client.intf.ClientTIMProcessor;
import cn.starboot.tim.common.ImConfig;

/**
 * Created by DELL(mxd) on 2022/1/6 20:25
 */
public class ImClientConfig extends ImConfig<ClientTIMProcessor> {

    private final ClientTIMProcessor clientProcessor;

	public ImClientConfig(ClientTIMProcessor clientProcessor, AioConfig aioConfig) {
		super(aioConfig);
		this.clientProcessor = clientProcessor;
	}

	@Override
	public ClientTIMProcessor getProcessor() {
        return this.clientProcessor;
    }
}
