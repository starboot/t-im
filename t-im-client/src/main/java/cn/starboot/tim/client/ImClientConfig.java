package cn.starboot.tim.client;

import cn.starboot.tim.client.intf.ClientProcessor;
import cn.starboot.tim.common.ImConfig;

/**
 * Created by DELL(mxd) on 2022/1/6 20:25
 */
public class ImClientConfig extends ImConfig {

    private final ClientProcessor clientProcessor;

	public ImClientConfig(ClientProcessor clientProcessor) {
		this.clientProcessor = clientProcessor;
	}

	public ClientProcessor getProcessor() {
        return this.clientProcessor;
    }
}
