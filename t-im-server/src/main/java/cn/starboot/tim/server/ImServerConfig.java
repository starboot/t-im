package cn.starboot.tim.server;

import cn.starboot.socket.core.AioConfig;
import cn.starboot.tim.common.ImConfig;
import cn.starboot.tim.server.cache.RedisConfig;
import cn.starboot.tim.server.cache.TIMPersistentHelper;
import cn.starboot.tim.server.cluster.ICluster;
import cn.starboot.tim.server.intf.TIMServerProcessor;

public class ImServerConfig extends ImConfig<TIMServerProcessor> {

	private final TIMServerProcessor serverProcessor;

	/**
	 * 开启持久化
	 */
	private boolean store;

	/**
	 * 集群助手
	 */
	private ICluster clusterHelper;

	private TIMPersistentHelper timPersistentHelper;

	private boolean out;
	/**
	 * 开启集群
	 */
	private boolean cluster;

	private boolean ssl;

	private String sslPath;

	private String sslPassword;

	private int monitorRate;

	private int heartPeriod;

	private int heartTimeout;

	private boolean heartPlugin;

	private boolean monitorPlugin;

	private final RedisConfig redisConfig = new RedisConfig();

	public ImServerConfig(TIMServerProcessor serverProcessor, AioConfig aioConfig) {
		super(aioConfig);
		this.serverProcessor = serverProcessor;
	}

	public TIMPersistentHelper getTimPersistentHelper() {
		return timPersistentHelper;
	}

	@Override
	public TIMServerProcessor getProcessor() {
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
		getAioConfig().setPort(port);
	}

	public void setHost(String host) {
		getAioConfig().setHost(host);
	}

	public int getPort() {
		return getAioConfig().getPort();
	}

	public String getHost() {
		return getAioConfig().getHost();
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

	public boolean isOut() {
		return out;
	}

	public void setOut(boolean out) {
		this.out = out;
	}

	public boolean isMonitorPlugin() {
		return monitorPlugin;
	}

	public void setMonitorPlugin(boolean monitorPlugin) {
		this.monitorPlugin = monitorPlugin;
	}

	public boolean isHeartPlugin() {
		return heartPlugin;
	}

	public void setHeartPlugin(boolean heartPlugin) {
		this.heartPlugin = heartPlugin;
	}

	@Override
	public String toString() {
		return "ImServerConfig{" +
				"store=" + store +
				", cluster=" + cluster +
				", ssl=" + ssl +
				", sslPath='" + sslPath + '\'' +
				", sslPassword='" + sslPassword + '\'' +
				", monitorRate=" + monitorRate +
				", heartPeriod=" + heartPeriod +
				", heartTimeout=" + heartTimeout +
				", monitorPlugin=" + monitorPlugin +
				", heartPlugin=" + heartPlugin +
				"," + super.toString() +
				'}';
	}
}
