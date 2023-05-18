package cn.starboot.tim.server;

import cn.hutool.core.util.ObjectUtil;
import cn.starboot.socket.Packet;
import cn.starboot.socket.core.ServerBootstrap;
import cn.starboot.socket.plugins.HeartPlugin;
import cn.starboot.socket.plugins.MonitorPlugin;
import cn.starboot.socket.utils.pool.memory.MemoryPool;
import cn.starboot.tim.common.banner.TimBanner;
import cn.starboot.tim.common.util.TIMLogUtil;
import cn.starboot.tim.server.intf.ServerTIMProcessor;
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

	protected TIMServerStarter(ServerTIMProcessor serverProcessor) {
		this.serverBootstrap
				= new ServerBootstrap("127.0.0.1",
				8888,
				ImServerProtocolHandler.getInstance(channelContext -> new ImServerChannelContext(channelContext, getImServerConfig())));
		this.imServerConfig = new ImServerConfig(serverProcessor, this.serverBootstrap.getConfig());
	}

	public static TIMServerStarter getInstance() {
		return getInstance(null);
	}

    public static synchronized TIMServerStarter getInstance(ServerTIMProcessor serverProcessor) {
        if (ObjectUtil.isNull(timServerStarter)){
            timServerStarter = new TIMServerStarter(serverProcessor);
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
            TIMLogUtil.info(LOGGER, "TIM server startup completed, taking: {} ms", iv);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void init() {
		TIMLogUtil.info(LOGGER, "init TIM server configuration");
        //加载配置信息
		TIMConfigManager.init(this.imServerConfig);
    }

	private void start0() {
		this.serverBootstrap
				.setMemoryPoolFactory(10 * 1024 * 1024, 10, true)
				.setThreadNum(1, 4)
				.setReadBufferSize(1024 * 50)
				.setWriteBufferSize(1024 * 10, 128)
				.addPlugin(new MonitorPlugin(60))
				.addPlugin(new HeartPlugin(60, 20, TimeUnit.SECONDS) {
					@Override
					public boolean isHeartMessage(Packet packet) {
						return false;
					}
				})
				.start();
		TIMLogUtil.info(LOGGER, "TIM server started successfully in ：{}:{}", "127.0.0.1", 8888);
	}

	public ImServerConfig getImServerConfig() {
		return this.imServerConfig;
	}

}
