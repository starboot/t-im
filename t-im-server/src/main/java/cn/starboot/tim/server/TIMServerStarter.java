package cn.starboot.tim.server;

import cn.hutool.core.util.ObjectUtil;
import cn.starboot.tim.common.banner.TimBanner;
import cn.starboot.tim.server.protocol.tcp.TCPSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by DELL(mxd) on 2021/12/23 20:45
 */
public class TIMServerStarter {

    private static final Logger log = LoggerFactory.getLogger(TIMServerStarter.class);

    private static TCPSocketServer tcpSocketServer;

    private static TIMServerStarter timServerStarter;

	static {
		TimBanner timBanner = new TimBanner();
		timBanner.printBanner(System.out);
	}

	protected TIMServerStarter() {}

    public static synchronized TIMServerStarter getInstance() {
        if (ObjectUtil.isNull(timServerStarter)){
            timServerStarter = new TIMServerStarter();
        }
        return timServerStarter;
    }

    public void start() {
        try {
			init();
			long start = System.currentTimeMillis();
            tcpSocketServer.start();
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
        // 实例化TCP服务器
        tcpSocketServer = TCPSocketServer.getInstance();
    }

    public static void main(String[] args) {
        TIMServerStarter timServerStarter = TIMServerStarter.getInstance();
        timServerStarter.start();
    }

}
