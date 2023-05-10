package cn.starboot.tim.server;

import cn.starboot.socket.core.AioConfig;
import cn.starboot.tim.common.ImConfig;
import cn.starboot.tim.server.cluster.ICluster;
import cn.starboot.tim.server.intf.ServerProcessor;

public class ImServerConfig extends ImConfig {

	private final ServerProcessor serverProcessor;

	/**
	 * 开启持久化
	 */
	public static boolean isStore;

	/**
	 * 集群助手
	 */
	public static ICluster clusterHelper;

	/**
	 * 开启集群
	 */
	public static boolean cluster;

	/**
	 * 心跳超时时间，单位：毫秒
	 */
	public static final int HEARTBEAT_TIMEOUT = 1000 * 60;

	public ImServerConfig(ServerProcessor serverProcessor, AioConfig aioConfig) {
		super(aioConfig);
		this.serverProcessor = serverProcessor;
	}

	public ServerProcessor getServerProcessor() {
		return this.serverProcessor;
	}
}
