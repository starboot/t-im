package org.tim.server;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.client.RedisConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.server.protocol.IMServer;
import org.tim.server.protocol.http.HTTPServer;
import org.tim.server.protocol.tcp.TCPSocketServer;
import org.tim.server.protocol.ws.WebSocketServer;
import org.tio.utils.SystemTimer;
import org.tio.utils.cache.redis.RedisCache;
import org.tio.utils.jfinal.P;

import java.util.Objects;

/**
 * Created by DELL(mxd) on 2021/12/23 20:45
 */
public class TIMServerStarter {

    private static final Logger log = LoggerFactory.getLogger(TIMServerStarter.class);

    private static TCPSocketServer tcpSocketServer;

    private static HTTPServer httpServer;

    private TIMServerStarter() {
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
        if (IMServer.isStore) {
            log.info("用户选择开启持久化，redis默认连接http://127.0.0.1:6379");
            try {
                RedissonClient redissonClient = Redisson.create();
                // timeToLiveSeconds : 生存时间   timeToIdleSeconds 计时器，  设置一个即可
                Long aLong = P.getLong("tim.redis.timeout");
                // 注册TIMServer
                RedisCache.register(redissonClient, "TIMServer", aLong, null);
            } catch (RedisConnectionException redisConnectionException) {
                log.error("Redis连接失败，持久化失效，请先启动本地Redis服务");
            }
        }
        if (IMServer.isUseSSL) {
            log.debug("开启ssl");
        }
        if (IMServer.heartbeat) {
            log.debug("开启心跳发送");
        }
    }

}
