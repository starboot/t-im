package org.tim.server.protocol.ws;

import org.tim.server.convert.websocketPackageConvert;
import org.tim.server.protocol.IMServer;
import org.tim.server.stat.TimGroupListener;
import org.tim.server.stat.TimIpStatListener;
import org.tio.server.ServerTioConfig;
import org.tio.utils.jfinal.P;
import org.tio.utils.time.Time;
import org.tio.websocket.server.WsServerConfig;
import org.tio.websocket.server.WsServerStarter;

import java.util.Objects;

/**
 * Created by DELL(mxd) on 2021/12/23 20:38
 */
public class WebSocketServer extends IMServer {

    private final ServerTioConfig sharedServerTioConfig;

    private static WebSocketServer webSocketServer;

    //这里将 SocketServer的ServerTioConfig传入
    private WebSocketServer(ServerTioConfig tioConfig){
        this.sharedServerTioConfig = tioConfig;
    }

    public synchronized static WebSocketServer getInstance(ServerTioConfig tioConfig) {
        if (Objects.isNull(webSocketServer)){
            webSocketServer = new WebSocketServer(tioConfig);
        }
        return webSocketServer;
    }

    @Override
    public void start() throws Exception{
        WsServerConfig config = new WsServerConfig(Integer.valueOf(P.get("websocket.port")));
        WsServerStarter wsServerStarter = new WsServerStarter(config,new ImWsHandler());
        ServerTioConfig wsServerTioConfig = wsServerStarter.getServerTioConfig();
        // 核心代码：共享TioConfig
        wsServerTioConfig.share(sharedServerTioConfig);
        // 设置IP监控 ；  注意的是：要保证下面两行代码的顺序，不能先addDuration()后setIpStatListener
        wsServerTioConfig.setIpStatListener(TimIpStatListener.me);
        // 设置ip统计时间段
        wsServerTioConfig.ipStats.addDuration(Time.MINUTE_1 * 5);
        // 设置群组监控
        wsServerTioConfig.setGroupListener(TimGroupListener.me);
        // 协议转换器，因为发送的消息包是 ImPacket,可以通过实现PacketConverter的convert方法将普通包转换为WsPacket包。
        wsServerTioConfig.packetConverter = new websocketPackageConvert();
        // 设置心跳超时时间
        wsServerTioConfig.setHeartbeatTimeout(HEARTBEAT_TIMEOUT);
        wsServerStarter.start();
    }

}
