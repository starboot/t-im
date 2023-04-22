package cn.starboot.tim.client.intf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * Created by DELL(mxd) on 2021/12/23 20:26
 */
public class ImSocketClientAioHandler implements ClientAioHandler {

    private static final Logger log = LoggerFactory.getLogger(ImSocketClientAioHandler.class);

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
    public void handler(Packet packet, ChannelContext channelContext) throws ImException {
        if (packet instanceof ImPacket) {
            ImPacket imPacket = (ImPacket) packet;
            AbstractCmdHandler command = CommandManager.getCommand(imPacket.getCommand());
            if (ObjectUtil.isNotEmpty(command)) {
                command.handler(packet, channelContext);
            }
        }
    }

    @Override
    public Packet heartbeatPacket(ChannelContext channelContext) {
        // 心跳包
        return new ImPacket(Command.COMMAND_HEARTBEAT_REQ, "{\"cmd\":13,\"hbbyte\":\"-127\"}".getBytes());
    }

}
