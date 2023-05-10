package cn.starboot.tim.common;

import cn.starboot.socket.core.AioConfig;

/**
 * Created by DELL(mxd) on 2022/1/6 17:07
 */
public abstract class ImConfig {

	public static final String name = "TIM";

	public static final String version = "3.0.0.v20221001-RELEASE";

	public static final String charset = "utf-8";

	public static final String authKey = "authKey";

	private final AioConfig aioConfig;

	public ImConfig(AioConfig aioConfig) {
		this.aioConfig = aioConfig;
	}

	public AioConfig getAioConfig() {
		return this.aioConfig;
	}

	public boolean isServer() {
		return this.aioConfig.isServer();
	}
}
