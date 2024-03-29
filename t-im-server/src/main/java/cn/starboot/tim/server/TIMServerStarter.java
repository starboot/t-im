package cn.starboot.tim.server;

import cn.starboot.socket.Packet;
import cn.starboot.socket.core.AioConfig;
import cn.starboot.socket.core.ServerBootstrap;
import cn.starboot.socket.plugins.ACKPlugin;
import cn.starboot.socket.plugins.HeartPlugin;
import cn.starboot.socket.plugins.MonitorPlugin;
import cn.starboot.tim.common.banner.TimBanner;
import cn.starboot.tim.common.util.TIMLogUtil;
import cn.starboot.tim.server.intf.TIMServerProcessor;
import cn.starboot.tim.server.intf.TIMDefaultServerProcessorImpl;
import cn.starboot.tim.server.handler.ImServerPacketProtocolHandler;
import cn.starboot.tim.server.protocol.tcp.ServerPrivateTcpProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by DELL(mxd) on 2021/12/23 20:45
 */
public class TIMServerStarter {

	private static final Logger LOGGER = LoggerFactory.getLogger(TIMServerStarter.class);

	private static TIMServerProcessor defaultServerProcessor;

	private final ServerBootstrap serverBootstrap;

	private final ImServerConfig imServerConfig;

	static {
		TimBanner timBanner = new TimBanner();
		timBanner.printBanner(System.out);
		defaultServerProcessor = new TIMDefaultServerProcessorImpl();
	}

	// 使用枚举构建单例模式
	private enum TIMServerStarterSingletonEnum {
		INSTANCE;
		private final TIMServerStarter timServerStarter;
		TIMServerStarterSingletonEnum() {
			timServerStarter = new TIMServerStarter(defaultServerProcessor);
		}
		private TIMServerStarter getTimServerStarter() {
			return timServerStarter;
		}
	}

	// 对外部提供的获取单例的方法
	public static TIMServerStarter getInstance() {
		return getInstance(null);
	}

	public static TIMServerStarter getInstance(TIMServerProcessor serverProcessor) {
		if (serverProcessor != null) {
			defaultServerProcessor = serverProcessor;
		}
		return TIMServerStarterSingletonEnum.INSTANCE.getTimServerStarter();
	}

	protected TIMServerStarter(TIMServerProcessor serverProcessor) {
		this.serverBootstrap
				= new ServerBootstrap(TIMConfigManager.getHost(),
				TIMConfigManager.getPort(),
				ServerPrivateTcpProtocol.getInstance(channelContext -> new ImServerChannelContext(channelContext, getImServerConfig()), new ImServerPacketProtocolHandler()));
		this.imServerConfig = new ImServerConfig(serverProcessor, this.serverBootstrap.getConfig());
	}

	public void start() {
		init();
		AioConfig aioConfig = getImServerConfig().getAioConfig();
		try {
			long start = System.currentTimeMillis();
			this.serverBootstrap.start();
			long end = System.currentTimeMillis();
			long iv = end - start;
			TIMLogUtil.info(LOGGER, "TIM server startup completed, taking {} ms", iv);
		} catch (Exception e) {
			this.serverBootstrap.shutdown();
			TIMLogUtil.info(LOGGER, "TIM server started failed.");
			e.printStackTrace();
			return;
		}
		TIMLogUtil.info(LOGGER, "TIM server started successfully in {}:{}", aioConfig.getHost(), aioConfig.getPort());
	}

	private void init() {
		TIMLogUtil.info(LOGGER, "Initialize TIM server configuration");
		try {
			// 加载配置信息
			TIMLogUtil.info(LOGGER, "loading TIM server configuration file");
			TIMConfigManager.Builder.build(this.imServerConfig).initTIMServerConfiguration().initRedisConfiguration().initKernelConfiguration();

			// 初始化内核设置
			TIMLogUtil.info(LOGGER, "Initialize TIM server kernel configuration");
			initKernel(this.serverBootstrap, getImServerConfig().getAioConfig());

			// 初始化插件
			TIMLogUtil.info(LOGGER, "Initialize TIM server plugin configuration");
			initPlugin(this.serverBootstrap, getImServerConfig());

			// 初始化集群
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		if (imServerConfig.isAckPlugin()) {
			serverBootstrap.addPlugin(new ACKPlugin(5, 1, TimeUnit.SECONDS));
		}
		TIMLogUtil.info(LOGGER, "Initialize TIM server plugin configuration completed");
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
		TIMLogUtil.info(LOGGER, "Initialize TIM server kernel configuration completed");
	}

}
