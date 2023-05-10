package cn.starboot.tim.server.protocol.tcp;

import cn.hutool.core.util.ObjectUtil;
import cn.starboot.socket.Packet;
import cn.starboot.socket.core.AioConfig;
import cn.starboot.socket.core.ServerBootstrap;
import cn.starboot.socket.plugins.HeartPlugin;
import cn.starboot.socket.plugins.MonitorPlugin;
import cn.starboot.socket.utils.pool.memory.MemoryPool;
import cn.starboot.tim.common.ImChannelContextFactory;
import cn.starboot.tim.server.ImServerChannelContext;
import cn.starboot.tim.server.ImServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by DELL(mxd) on 2021/12/23 20:28
 */
public class TCPSocketServer {

	private static final Logger log = LoggerFactory.getLogger(TCPSocketServer.class);

	private ImServerConfig imServerConfig;

    private static TCPSocketServer socketServer;

    private TCPSocketServer() {
    }

    public synchronized static TCPSocketServer getInstance() {
        if (ObjectUtil.isNull(socketServer)){
            socketServer = new TCPSocketServer();
        }
        return socketServer;
    }

    private final ImChannelContextFactory<ImServerChannelContext> serverImChannelContextFactory = (channelContext) -> new ImServerChannelContext(channelContext, getImServerConfig());

    public void start() throws IOException {
        ServerBootstrap serverBootstrap = new ServerBootstrap("127.0.0.1", 8888, ImServerProtocolHandler.getInstance(serverImChannelContextFactory));
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
        log.info("TCP服务器启动在：{}:{}", "127.0.0.1", 8888);
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
