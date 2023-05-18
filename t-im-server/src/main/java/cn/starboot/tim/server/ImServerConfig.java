package cn.starboot.tim.server;

import cn.starboot.socket.core.AioConfig;
import cn.starboot.tim.common.ImConfig;
import cn.starboot.tim.server.cache.RedisConfig;
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

	private boolean ssl;

	private String sslPath;

	private String sslPassword;

	private int monitorRate;
	private int heartPeriod;
	private int heartTimeout;

	private final RedisConfig redisConfig = new RedisConfig();

	public ImServerConfig(ServerTIMProcessor serverProcessor, AioConfig aioConfig) {
		super(aioConfig);
		this.serverProcessor = serverProcessor;
	}

	public TIMPersistentHelper getTimPersistentHelper() {
		return timPersistentHelper;
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

	public void setStore(boolean store) {
		this.store = store;
	}

	public void setCluster(boolean cluster) {
		this.cluster = cluster;
	}

	public RedisConfig getRedisConfig() {
		return redisConfig;
	}

	public void setPort(int port) {

	}

	public void setHost(String host) {

	}

	public boolean isSsl() {
		return ssl;
	}

	public void setSsl(boolean ssl) {
		this.ssl = ssl;
	}

	public String getSslPath() {
		return sslPath;
	}

	public void setSslPath(String sslPath) {
		this.sslPath = sslPath;
	}

	public String getSslPassword() {
		return sslPassword;
	}

	public void setSslPassword(String sslPassword) {
		this.sslPassword = sslPassword;
	}

	public int getMonitorRate() {
		return monitorRate;
	}

	public void setMonitorRate(int monitorRate) {
		this.monitorRate = monitorRate;
	}

	public int getHeartPeriod() {
		return heartPeriod;
	}

	public void setHeartPeriod(int heartPeriod) {
		this.heartPeriod = heartPeriod;
	}

	public int getHeartTimeout() {
		return heartTimeout;
	}

	public void setHeartTimeout(int heartTimeout) {
		this.heartTimeout = heartTimeout;
	}
}
