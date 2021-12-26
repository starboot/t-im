package org.tim.client;

import org.tim.common.ImPacket;
import org.tim.common.codec.TCPCodec;
import org.tim.common.packets.Command;
import org.tio.client.intf.ClientAioHandler;
import org.tio.core.ChannelContext;
import org.tio.core.TioConfig;
import org.tio.core.exception.TioDecodeException;
import org.tio.core.intf.Packet;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Created by DELL(mxd) on 2021/12/23 20:26
 */
public class ImSocketClientAioHandler implements ClientAioHandler {

    private static final TCPCodec tcpcodec = TCPCodec.getInstance();

    @Override
    public Packet decode(ByteBuffer byteBuffer, int i, int i1, int i2, ChannelContext channelContext) throws TioDecodeException {
        return tcpcodec.decode(byteBuffer, i, i1, i2, channelContext);
    }

    @Override
    public ByteBuffer encode(Packet packet, TioConfig tioConfig, ChannelContext channelContext) {
        return tcpcodec.encode(packet, tioConfig, channelContext);
    }

    @Override
    public void handler(Packet packet, ChannelContext channelContext) {

        if (packet instanceof ImPacket) {
            ImPacket packet1 = (ImPacket) packet;
            byte[] body = packet1.getBody();
            String s = new String(body, StandardCharsets.UTF_8);
            System.out.println("收到消息:" + s);
//            Tio.send(channelContext, packet);
        }

    }

    @Override
    public Packet heartbeatPacket(ChannelContext channelContext) {
        // 心跳包
        return new ImPacket(Command.COMMAND_HEARTBEAT_REQ, "{\"cmd\":13,\"hbbyte\":\"-127\"}".getBytes());
    }
}
