package cn.starboot.tim.server.protocol.tcp;

import cn.hutool.core.util.ObjectUtil;
import cn.starboot.socket.Packet;
import cn.starboot.socket.core.AioConfig;
import cn.starboot.socket.core.ChannelContext;
import cn.starboot.socket.core.ServerBootstrap;
import cn.starboot.socket.plugins.HeartPlugin;
import cn.starboot.socket.plugins.MonitorPlugin;
import cn.starboot.socket.utils.pool.memory.MemoryPool;
import cn.starboot.tim.common.ImChannelContextFactory;
import cn.starboot.tim.common.ImConfig;
import cn.starboot.tim.server.ImServerChannelContext;
import cn.starboot.tim.server.ImServerConfig;
import cn.starboot.tim.server.protocol.IMServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by DELL(mxd) on 2021/12/23 20:28
 */
public class TCPSocketServer extends IMServer {

    private static AioConfig serverTioConfig;

    private static TCPSocketServer socketServer;

    private static final Logger log = LoggerFactory.getLogger(TCPSocketServer.class);

    private TCPSocketServer() {
    }

    public synchronized static TCPSocketServer getInstance() {
        if (ObjectUtil.isNull(socketServer)){
            socketServer = new TCPSocketServer();
        }
        return socketServer;
    }

    private ImServerConfig imServerConfig;

    private final ImChannelContextFactory<ImServerChannelContext> serverImChannelContextFactory = (channelContext, imConfig) -> new ImServerChannelContext(channelContext, getImServerConfig());

    @Override
    public void start() throws IOException {

        ServerBootstrap serverBootstrap = new ServerBootstrap(IMServer.ip, IMServer.port, ImServerProtocolHandler.getInstance());
        serverBootstrap.setMemoryPoolFactory(() -> new MemoryPool(10 * 1024 * 1024, 10, true))
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
        imServerConfig = new ImServerConfig(null, serverBootstrap.getConfig());
        log.info("TCP服务器启动在：{}:{}", IMServer.ip, IMServer.port);
    }

    /**
     *
     * 获取websocket和TCP协议共享的ServerTioConfig
     * @return .
     */
    public ImServerConfig getImServerConfig() {
        return imServerConfig;
    }
}
