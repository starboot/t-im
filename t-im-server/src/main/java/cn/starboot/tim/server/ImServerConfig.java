package cn.starboot.tim.server;

import cn.starboot.tim.common.ImConfig;
import cn.starboot.tim.server.cluster.ICluster;
import cn.starboot.tim.server.intf.DefaultServerTIMProcessor;
import cn.starboot.tim.server.intf.ServerTIMProcessor;

public class ImServerConfig extends ImConfig<ServerTIMProcessor> {

	private final ServerTIMProcessor serverProcessor;

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

	public ImServerConfig(ServerTIMProcessor serverProcessor) {
		this.serverProcessor = serverProcessor;
	}

	@Override
	public ServerTIMProcessor getProcessor() {
		return this.serverProcessor;
	}
}
