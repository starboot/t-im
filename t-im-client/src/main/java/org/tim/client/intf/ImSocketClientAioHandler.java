package org.tim.client.intf;

import cn.hutool.core.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.client.command.AbstractCmdHandler;
import org.tim.client.command.CommandManager;
import org.tim.common.ImPacket;
import org.tim.common.codec.TCPCodec;
import org.tim.common.exception.ImException;
import org.tim.common.packets.Command;
import org.tio.client.intf.ClientAioHandler;
import org.tio.core.ChannelContext;
import org.tio.core.TioConfig;
import org.tio.core.exception.TioDecodeException;
import org.tio.core.intf.Packet;

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
