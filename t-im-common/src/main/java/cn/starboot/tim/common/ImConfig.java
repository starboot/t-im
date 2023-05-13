package cn.starboot.tim.common;

import cn.starboot.socket.core.AioConfig;
import cn.starboot.tim.common.intf.TIMProcessor;

/**
 * Created by DELL(mxd) on 2022/1/6 17:07
 */
public abstract class ImConfig<P extends TIMProcessor> {

	public static final String name = "TIM";

	public static final String version = "3.0.0.v20221001-RELEASE";

	public static final String charset = "utf-8";

	public static final String authKey = "authKey";

	private AioConfig aioConfig;

	/**
	 * 本机IP
	 */
	public static String ip = "127.0.0.1";

	/**
	 * 本地tcp端口
	 */
	public static int port = 8888;

	/**
	 * 开启SSL
	 */
	public static boolean isUseSSL;

	public void setAioConfig(AioConfig aioConfig) {
		this.aioConfig = aioConfig;
	}

	public AioConfig getAioConfig() {
		return this.aioConfig;
	}

	public boolean isServer() {
		return this.aioConfig.isServer();
	}

	public abstract P getProcessor();
}
