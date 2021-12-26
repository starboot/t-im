package org.tim.server.protocol.tcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.common.ImPacket;
import org.tim.common.codec.TCPCodec;
import org.tim.common.exception.ImException;
import org.tim.common.packets.Command;
import org.tim.server.command.AbstractCmdHandler;
import org.tim.server.command.CommandManager;
import org.tio.core.ChannelContext;
import org.tio.core.TioConfig;
import org.tio.core.exception.TioDecodeException;
import org.tio.core.intf.Packet;
import org.tio.server.intf.ServerAioHandler;
import org.tio.websocket.common.WsResponse;

import java.nio.ByteBuffer;

/**
 * Created by DELL(mxd) on 2021/12/23 20:26
 */
public class ImSocketServerAioHandler implements ServerAioHandler {

    private static final Logger log = LoggerFactory.getLogger(ImSocketServerAioHandler.class);

    private static final TCPCodec tcpcodec = TCPCodec.getInstance();

    @Override
    public Packet decode(ByteBuffer byteBuffer, int i, int i1, int i2, ChannelContext channelContext) throws TioDecodeException {

        return tcpcodec.decode(byteBuffer, i, i1, i2, channelContext);
    }

    @Override
    public ByteBuffer encode(Packet packet, TioConfig tioConfig, ChannelContext channelContext) {

        if (packet instanceof ImPacket){
            log.debug("TCP包直接发送");
            return tcpcodec.encode(packet, tioConfig, channelContext);
        }else if (packet instanceof WsResponse){
            log.debug("WS包，转换一下");
            packet = tioConfig.packetConverter.convert(packet,channelContext);
        }
        return tcpcodec.encode(packet, tioConfig, channelContext);
    }

    @Override
    public void handler(Packet packet, ChannelContext channelContext) throws ImException {

        if (packet instanceof ImPacket) {
            ImPacket packet1 = (ImPacket) packet;
            Command command = packet1.getCommand();
            AbstractCmdHandler cmdHandler = CommandManager.getCommand(command);
            cmdHandler.handler(packet, channelContext);
        }
    }
}
