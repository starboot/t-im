package org.tim.client;

import org.tio.client.ClientChannelContext;
import org.tio.client.ClientTioConfig;
import org.tio.client.ReconnConf;
import org.tio.client.TioClient;
import org.tio.core.Node;

/**
 * Created by DELL(mxd) on 2021/12/23 20:33
 */
public class TioSocketClient {

    private static void start() throws Exception {
        ClientTioConfig clientTioConfig = new ClientTioConfig(new ImSocketClientAioHandler(), new ImSocketClientAioListener(), new ReconnConf(2000));
        TioClient client = new TioClient(clientTioConfig);
        ClientChannelContext clientChannelContext = client.connect(new Node("127.0.0.1", 8888));
        Thread.sleep(1000);

//        ImPacket imPacket = ImPacket.newPacket("你好啊".getBytes());
//        Tio.send(clientChannelContext, imPacket);
    }

    public static void main(String[] args) throws Exception {
        start();

    }

}
