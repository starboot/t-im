package org.tim.server;

import cn.hutool.core.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.common.IMConfig;
import org.tim.common.exception.ImException;
import org.tim.server.cluster.ClusterData;
import org.tim.server.cluster.codec.ClusterClientAioHandler;
import org.tim.server.cluster.codec.ClusterClientAioListener;
import org.tim.server.protocol.IMServer;
import org.tim.server.protocol.http.HTTPServer;
import org.tim.server.protocol.tcp.TCPSocketServer;
import org.tim.server.protocol.ws.WebSocketServer;
import org.tio.client.*;
import org.tio.core.Node;
import org.tio.core.Tio;
import org.tio.utils.SystemTimer;
import org.tio.utils.jfinal.P;

import java.util.Objects;

/**
 * Created by DELL(mxd) on 2021/12/23 20:45
 */

public class TIMServerStarter {

    private static final Logger log = LoggerFactory.getLogger(TIMServerStarter.class);

    private static TCPSocketServer tcpSocketServer;

    private static HTTPServer httpServer;

    protected TIMServerStarter() {
    }

    private static TIMServerStarter timServerStarter;

    public static synchronized TIMServerStarter getInstance() {
        if (Objects.isNull(timServerStarter)){
            timServerStarter = new TIMServerStarter();
        }
        return timServerStarter;
    }

    public void start() {
        try {
            long start = SystemTimer.currTime;
            tcpSocketServer.start();
            // 实例化WebSocket服务器, 由于websocket服务器需要用到TCP服务器的配置信息，则不在初始化的时候获取其实例对象
            WebSocketServer webSocketServer = WebSocketServer.getInstance(TCPSocketServer.getServerTioConfig());
            webSocketServer.start();
            httpServer.start();
            long end = SystemTimer.currTime;
            long iv = end - start;
            if (IMServer.cluster && IMServer.isStore) {
                IMServer.clusterHelper.registerCluster(IMServer.ip, IMServer.port);
            }
            if (log.isInfoEnabled()) {
                log.info("Server启动完毕,耗时:{}ms", iv);
            }
            log.info("TCP Socket Server on port: {}, Websocket Server on port: {}, HTTP Server on port: {}",
                    P.get("tcp.port"), P.get("websocket.port"), P.get("http.port"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void init() {
        //加载配置信息
        P.use("tim.properties");
        // 实例化TCP服务器
        tcpSocketServer = TCPSocketServer.getInstance();
        // 实例化HTTP服务器
        httpServer = HTTPServer.getInstance();

        if (IMServer.cluster && IMServer.isStore) {
            // 集群初始化
            ClientTioConfig clientTioConfig = new ClientTioConfig(new ClusterClientAioHandler(), new ClusterClientAioListener(), new ReconnConf(2000));
            try {
                TioClient client = new TioClient(clientTioConfig);
                ClusterData cluster = IMServer.clusterHelper.getCluster();
                if (ObjectUtil.isNotEmpty(cluster)) {
                    cluster.list.forEach(s -> {
                        if (s.equals(IMServer.ip + ":" + IMServer.port)) {
                            return;
                        }
                        try {
                            String[] split = s.split(":");
                            if (split.length == 2) {
                                ClientChannelContext clientChannelContext = client.connect(new Node(split[0], Integer.parseInt(split[1])));
                                Tio.bindUser(clientChannelContext, s);
                            }else {
                                throw new ImException("集群服务器IP：port格式错误");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (IMServer.isUseSSL) {
            log.debug("开启ssl");
        }
        if (IMServer.heartbeat) {
            log.debug("开启心跳发送");
        }
        // 配置服务器读取command的路径
        IMConfig.DEFAULT_CLASSPATH_CONFIGURATION_FILE = "org/tim/server/command/command.properties";
    }

}
