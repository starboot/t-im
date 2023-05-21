package cn.starboot.tim.server;

import cn.hutool.core.util.ObjectUtil;
import cn.starboot.socket.Packet;
import cn.starboot.socket.core.AioConfig;
import cn.starboot.socket.core.ServerBootstrap;
import cn.starboot.socket.plugins.HeartPlugin;
import cn.starboot.socket.plugins.MonitorPlugin;
import cn.starboot.tim.common.banner.TimBanner;
import cn.starboot.tim.common.util.TIMLogUtil;
import cn.starboot.tim.server.intf.TIMServerProcessor;
import cn.starboot.tim.server.intf.TIMServerProcessorImpl;
import cn.starboot.tim.server.protocol.tcp.ImServerProtocolHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by DELL(mxd) on 2021/12/23 20:45
 */
public class TIMServerStarter {

	private static final Logger LOGGER = LoggerFactory.getLogger(TIMServerStarter.class);

	private final ServerBootstrap serverBootstrap;

	private static TIMServerStarter timServerStarter;

	private final ImServerConfig imServerConfig;

	static {
		TimBanner timBanner = new TimBanner();
		timBanner.printBanner(System.out);
	}

	protected TIMServerStarter(TIMServerProcessor serverProcessor) {
		this.serverBootstrap
				= new ServerBootstrap(TIMConfigManager.getHost(),
				TIMConfigManager.getPort(),
				ImServerProtocolHandler.getInstance(channelContext -> new ImServerChannelContext(channelContext, getImServerConfig())));
		this.imServerConfig = new ImServerConfig(serverProcessor, this.serverBootstrap.getConfig());
	}

	public static synchronized TIMServerStarter getInstance() {
		if (ObjectUtil.isNull(timServerStarter)) {
			timServerStarter = new TIMServerStarter(new TIMServerProcessorImpl());
		}
		return timServerStarter;
	}

	public void start() {
		try {
			init();
			long start = System.currentTimeMillis();
			start0();
			long end = System.currentTimeMillis();
			long iv = end - start;
			TIMLogUtil.info(LOGGER, "TIM server startup completed, taking {} ms", iv);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void init() {
		TIMLogUtil.info(LOGGER, "init TIM server configuration");
		try {
			// 加载配置信息
			TIMLogUtil.info(LOGGER, "loading TIM server configuration file");
			TIMConfigManager.Builder.build(this.imServerConfig).initTIMServerConfiguration().initRedisConfiguration().initKernelConfiguration();

			// 初始化内核设置
			TIMLogUtil.info(LOGGER, "init TIM server kernel configuration");
			initKernel(this.serverBootstrap, getImServerConfig().getAioConfig());

			// 初始化插件
			TIMLogUtil.info(LOGGER, "init TIM server plugin configuration");
			initPlugin(this.serverBootstrap, getImServerConfig());

			// 初始化集群
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void start0() {
		AioConfig aioConfig = getImServerConfig().getAioConfig();
		this.serverBootstrap.start();
		TIMLogUtil.info(LOGGER, "TIM server started successfully in {}:{}", aioConfig.getHost(), aioConfig.getPort());
	}

	public ImServerConfig getImServerConfig() {
		return this.imServerConfig;
	}

	private void initPlugin(ServerBootstrap serverBootstrap, ImServerConfig imServerConfig) {
		if (imServerConfig.isMonitorPlugin()) {
			serverBootstrap.addPlugin(new MonitorPlugin(getImServerConfig().getMonitorRate()));
		}
		if (imServerConfig.isHeartPlugin()) {
			serverBootstrap.addPlugin(new HeartPlugin(getImServerConfig().getHeartTimeout(),
					getImServerConfig().getHeartPeriod(),
					TimeUnit.SECONDS) {
				@Override
				public boolean isHeartMessage(Packet packet) {
					return false;
				}
			});
		}

	}

	private void initKernel(ServerBootstrap serverBootstrap, AioConfig aioConfig) {
		serverBootstrap
				.setMemoryPoolFactory(aioConfig.getMemoryBlockSize(),
						aioConfig.getMemoryBlockNum(),
						aioConfig.isDirect())
				.setThreadNum(aioConfig.getBossThreadNumber(),
						aioConfig.getWorkerThreadNumber())
				.setReadBufferSize(aioConfig.getReadBufferSize())
				.setWriteBufferSize(aioConfig.getWriteBufferSize(), 128);
	}

}
