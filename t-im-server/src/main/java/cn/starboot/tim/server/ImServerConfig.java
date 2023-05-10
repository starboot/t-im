package cn.starboot.tim.server;

import cn.starboot.socket.core.AioConfig;
import cn.starboot.tim.common.ImConfig;
import cn.starboot.tim.server.intf.ServerProcessor;

public class ImServerConfig extends ImConfig {

	private final ServerProcessor serverProcessor;

	public ImServerConfig(ServerProcessor serverProcessor, AioConfig aioConfig) {
		super(aioConfig);
		this.serverProcessor = serverProcessor;
	}

	public ServerProcessor getServerProcessor() {
		return this.serverProcessor;
	}
}
