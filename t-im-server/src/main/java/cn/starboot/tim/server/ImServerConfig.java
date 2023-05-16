package cn.starboot.tim.server;

import cn.starboot.socket.core.AioConfig;
import cn.starboot.tim.common.ImConfig;
import cn.starboot.tim.server.cache.TIMPersistentHelper;
import cn.starboot.tim.server.cluster.ICluster;
import cn.starboot.tim.server.intf.DefaultServerTIMProcessor;
import cn.starboot.tim.server.intf.ServerTIMProcessor;

public class ImServerConfig extends ImConfig<ServerTIMProcessor> {

	private final ServerTIMProcessor serverProcessor;

	/**
	 * 开启持久化
	 */
	public boolean store;

	/**
	 * 集群助手
	 */
	public ICluster clusterHelper;

	private TIMPersistentHelper timPersistentHelper;

	/**
	 * 开启集群
	 */
	public boolean cluster;

	/**
	 * 心跳超时时间，单位：毫秒
	 */
	public final int HEARTBEAT_TIMEOUT = 1000 * 60;

	public ImServerConfig(ServerTIMProcessor serverProcessor) {
		this.serverProcessor = serverProcessor;
	}

	public TIMPersistentHelper getTimPersistentHelper() {
		return timPersistentHelper;
	}

	@Override
	protected void setAioConfig(AioConfig aioConfig) {
		this.aioConfig = aioConfig;
	}

	@Override
	public ServerTIMProcessor getProcessor() {
		return this.serverProcessor;
	}

	public boolean isStore() {
		return store;
	}

	public ICluster getClusterHelper() {
		return clusterHelper;
	}

	public boolean isCluster() {
		return cluster;
	}
}
