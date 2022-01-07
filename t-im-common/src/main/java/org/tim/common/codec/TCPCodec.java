package org.tim.common.codec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.common.ImPacket;
import org.tim.common.ImStatus;
import org.tim.common.packets.Command;
import org.tio.core.ChannelContext;
import org.tio.core.TioConfig;
import org.tio.core.exception.TioDecodeException;
import org.tio.core.intf.Packet;
import org.tio.websocket.common.WsResponse;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Objects;

/**
 * Created by DELL(mxd) on 2021/12/24 20:52
 */
public class TCPCodec implements Encoder, Decoder{

    private static final Logger log = LoggerFactory.getLogger(TCPCodec.class);

    @Override
    public Packet decode(ByteBuffer buffer, int i, int i1, int i2, ChannelContext channelContext) throws TioDecodeException {

        //cmd命令码
        byte cmdByte = buffer.get();
        if(Command.forNumber(cmdByte) == null){
            throw new TioDecodeException(ImStatus.C10014.getText());
        }
        int bodyLen = buffer.getInt();
        //数据不正确，则抛出ImDecodeException异常
        if (bodyLen < 0)
        {
            throw new TioDecodeException("bodyLength [" + bodyLen + "] is not right, remote:" + channelContext.getId());
        }
        int readableLength = buffer.limit() - buffer.position();
        int validateBodyLen = readableLength - bodyLen;
        // 不够消息体长度(剩下的buffer组不了消息体)
        if (validateBodyLen < 0)
        {
            return null;
        }
        byte[] body = new byte[bodyLen];
        try{
            buffer.get(body,0,bodyLen);
        }catch(Exception e){
            log.error(e.toString());
        }
        log.debug("TCP解码成功...");
        //byteBuffer的总长度是 = 1byte协议版本号+1byte消息标志位+4byte同步序列号(如果是同步发送则多4byte同步序列号,否则无4byte序列号)+1byte命令码+4byte消息的长度+消息体的长度
        ImPacket tcpPacket = new ImPacket(Command.forNumber(cmdByte), body);
        tcpPacket.setByteCount(bodyLen);
        return tcpPacket;
    }

    @Override
    public ByteBuffer encode(Packet packet, TioConfig tioConfig, ChannelContext channelContext) {
        ImPacket packet1 = null;
        if (packet instanceof ImPacket){
            packet1 = (ImPacket) packet;
        }
        if (packet instanceof WsResponse){
            log.error("WS包转TCP未成功");
        }
        // 1byte命令码+4byte消息的长度+消息体
        assert packet1 != null;
        int bodyLength = packet1.getBody().length;
        ByteBuffer buffer = ByteBuffer.allocate(bodyLength + 4 + 1);
        byte cmdByte = 0x00;
        //消息类型;
        if(packet1.getCommand() != null) {
            cmdByte = (byte) (cmdByte | packet1.getCommand().getNumber());
        }
        //设置字节序
        ByteOrder byteOrder = channelContext.getTioConfig() == null ? ByteOrder.BIG_ENDIAN : channelContext.getTioConfig().getByteOrder();
        buffer.order(byteOrder);
        buffer.put(cmdByte);
        buffer.putInt(bodyLength);
        buffer.put(packet1.getBody());
        return buffer;
    }

    private static TCPCodec tcpCodec;

    private TCPCodec() {
    }

    public static TCPCodec getInstance() {
        if (Objects.isNull(tcpCodec)){
            tcpCodec = new TCPCodec();
        }
        return tcpCodec;
    }
}
