package cn.starboot.tim.server;

import cn.hutool.core.util.ObjectUtil;
import cn.starboot.socket.Packet;
import cn.starboot.socket.core.ServerBootstrap;
import cn.starboot.socket.plugins.HeartPlugin;
import cn.starboot.socket.plugins.MonitorPlugin;
import cn.starboot.socket.utils.pool.memory.MemoryPool;
import cn.starboot.tim.common.ImChannelContextFactory;
import cn.starboot.tim.common.banner.TimBanner;
import cn.starboot.tim.server.intf.ServerProcessor;
import cn.starboot.tim.server.protocol.tcp.ImServerProtocolHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by DELL(mxd) on 2021/12/23 20:45
 */
public class TIMServerStarter {

    private static final Logger log = LoggerFactory.getLogger(TIMServerStarter.class);

    private static TIMServerStarter timServerStarter;

	private final ImServerConfig imServerConfig;

	private final ImChannelContextFactory<ImServerChannelContext> serverImChannelContextFactory
			= (channelContext) -> new ImServerChannelContext(channelContext, getImServerConfig());

	static {
		TimBanner timBanner = new TimBanner();
		timBanner.printBanner(System.out);
	}

	protected TIMServerStarter(ServerProcessor serverProcessor) {
		this.imServerConfig = new ImServerConfig(serverProcessor);
	}

	public static TIMServerStarter getInstance() {
		return getInstance(null);
	}

    public static synchronized TIMServerStarter getInstance(ServerProcessor serverProcessor) {
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
            if (log.isInfoEnabled()) {
                log.info("Server启动完毕,耗时:{}ms", iv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void init() {
        //加载配置信息
//        P.use("tim.properties");

    }

	private void start0() {
		ServerBootstrap serverBootstrap
				= new ServerBootstrap(getImServerConfig().getAioConfig().getHost(),
				getImServerConfig().getAioConfig().getPort(),
				ImServerProtocolHandler.getInstance(serverImChannelContextFactory));
		serverBootstrap
				.setMemoryPoolFactory(() -> new MemoryPool(10 * 1024 * 1024, 10, true))
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
		getImServerConfig().setAioConfig(serverBootstrap.getConfig());
		log.info("TCP服务器启动在：{}:{}", "127.0.0.1", 8888);
	}

	public ImServerConfig getImServerConfig() {
		return this.imServerConfig;
	}

    public static void main(String[] args) {
        TIMServerStarter timServerStarter = TIMServerStarter.getInstance();
        timServerStarter.start();
    }

}
