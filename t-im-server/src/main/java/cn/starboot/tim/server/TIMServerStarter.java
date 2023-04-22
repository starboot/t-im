package cn.starboot.tim.server;

import cn.hutool.core.util.ObjectUtil;
import cn.starboot.socket.utils.SystemTimer;
import cn.starboot.tim.common.IMConfig;
import cn.starboot.tim.server.protocol.IMServer;
import cn.starboot.tim.server.protocol.tcp.TCPSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by DELL(mxd) on 2021/12/23 20:45
 */

public class TIMServerStarter {

    private static final Logger log = LoggerFactory.getLogger(TIMServerStarter.class);

    private static TCPSocketServer tcpSocketServer;

    protected TIMServerStarter() {
    }

    private static TIMServerStarter timServerStarter;

    public static synchronized TIMServerStarter getInstance() {
        if (ObjectUtil.isNull(timServerStarter)){
            timServerStarter = new TIMServerStarter();
        }
        return timServerStarter;
    }

    public void start() {
        try {
            long start = SystemTimer.currTime;
            tcpSocketServer.start();
            long end = SystemTimer.currTime;
            long iv = end - start;
            if (IMServer.cluster && IMServer.isStore) {
//                IMServer.clusterHelper.registerCluster(IMServer.ip, IMServer.port);
            }
            if (log.isInfoEnabled()) {
                log.info("Server启动完毕,耗时:{}ms", iv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void init() {
        //加载配置信息
//        P.use("tim.properties");
        // 实例化TCP服务器
        tcpSocketServer = TCPSocketServer.getInstance();



        if (IMServer.isUseSSL) {
            log.debug("开启ssl");
        }
        if (IMServer.heartbeat) {
            log.debug("开启心跳发送");
        }
        // 配置服务器读取command的路径
        IMConfig.DEFAULT_CLASSPATH_CONFIGURATION_FILE = "cn/starboot/tim/server/command/command.properties";
    }

}
