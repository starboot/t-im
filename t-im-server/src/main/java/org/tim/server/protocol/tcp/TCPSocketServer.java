package org.tim.server.protocol.tcp;

import org.tim.server.convert.tcpPackageConvert;
import org.tim.server.protocol.IMServer;
import org.tim.server.stat.TimGroupListener;
import org.tim.server.stat.TimIpStatListener;
import org.tio.server.ServerTioConfig;
import org.tio.server.TioServer;
import org.tio.utils.jfinal.P;
import org.tio.utils.time.Time;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by DELL(mxd) on 2021/12/23 20:28
 */
public class TCPSocketServer extends IMServer {

    private static ServerTioConfig serverTioConfig;

    private static TCPSocketServer socketServer;

    private TCPSocketServer() {
    }

    public synchronized static TCPSocketServer getInstance() {
        if (Objects.isNull(socketServer)){
            socketServer = new TCPSocketServer();
        }
        return socketServer;
    }

    @Override
    public void start() throws IOException {
        serverTioConfig = new ServerTioConfig("Tio Socket Server", new ImSocketServerAioHandler(), new ImSocketServerAioListener());
        TioServer server = new TioServer(serverTioConfig);
        server.getServerTioConfig().setReadBufferSize(20480);
        // 设置tio流量控制台
        server.getServerTioConfig().debug = P.getBoolean("tio.server.group.stat.debug", false);
        // 设置IP监控 ；  注意的是：要保证下面两行代码的顺序，不能先addDuration()后setIpStatListener
        server.getServerTioConfig().setIpStatListener(TimIpStatListener.me);
        // 设置ip统计时间段
        server.getServerTioConfig().ipStats.addDuration(Time.MINUTE_1 * 5);
        // 设置群组监控
        server.getServerTioConfig().setGroupListener(TimGroupListener.me);
        server.getServerTioConfig().packetConverter = new tcpPackageConvert();
        // 设置心跳超时时间
        server.getServerTioConfig().setHeartbeatTimeout(HEARTBEAT_TIMEOUT);
        server.start("127.0.0.1", Integer.parseInt(P.get("tcp.port")));
    }

    /**
     *
     * 获取websocket和TCP协议共享的ServerTioConfig
     * @return .
     */
    public static ServerTioConfig getServerTioConfig() {
        return serverTioConfig;
    }
}
