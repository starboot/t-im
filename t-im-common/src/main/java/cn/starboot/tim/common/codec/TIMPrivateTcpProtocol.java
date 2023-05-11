package cn.starboot.tim.common.codec;

import cn.starboot.socket.Packet;
import cn.starboot.socket.core.Aio;
import cn.starboot.socket.core.ChannelContext;
import cn.starboot.socket.core.WriteBuffer;
import cn.starboot.socket.enums.ProtocolEnum;
import cn.starboot.socket.exception.AioDecoderException;
import cn.starboot.socket.exception.AioEncoderException;
import cn.starboot.socket.intf.AioHandler;
import cn.starboot.socket.utils.AIOUtil;
import cn.starboot.socket.utils.pool.memory.MemoryUnit;
import cn.starboot.tim.common.ImConst;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * 使用aio-socket，对Google的Protobuf进行解编码
 *
 * Created by DELL(mxd) on 2021/12/24 20:52
 */
public abstract class TIMPrivateTcpProtocol implements AioHandler, ImConst {

    private static final Logger LOGGER = LoggerFactory.getLogger(TIMPrivateTcpProtocol.class);

    // TIM系统定制私有TCP通讯协议
    private static TIMPrivateTcpProtocol timPrivateTcpProtocol;

    // 协议版本号
    private static final byte timProtocolVersion = (byte) 0x01;

    // 此消息为t-im系统的标识
    private static final byte timProtocolMark = (byte) 0xff;

    // TIM定制私有协议最少比特位: 1(版本号:version) + 1(系统标识:mark) + 4(命令码:commandType) + 4(消息体长度:length)
    private static final int minLength = 10;

    // 此对象不让用户自己实例化
    protected TIMPrivateTcpProtocol() {
    }

    @Override
    public Packet decode(MemoryUnit readBuffer, ChannelContext channelContext) throws AioDecoderException {
        // 开始解码
        ByteBuffer buffer = readBuffer.buffer();
        // 获取TCP报文的长度
        int remaining = buffer.remaining();
        // 定制TCP消息的最短长度为 minLength
        if (remaining < minLength) {
            return null;
        }
        // 长度都用，开始解码；首先先标记，出错可以重置比特流对象的指针
        buffer.mark();
        // 获取版本号
        final byte version = buffer.get();
        // 获取标识位
        final byte mark = buffer.get();
        // 判断该消息是否为TIM官方协议
        if (version != timProtocolVersion && mark != timProtocolMark) {
            // 重置指针
            buffer.reset();
            return null;
        }
        // 获取命令码数字索引
        final int commandType = buffer.getInt();
        // 获取命令码
        TIMCommandType command = TIMCommandType.getCommandTypeByCode(commandType);
        // 判断是否为异常命令
        if (command == null) {
            // 用户发送未知命令码
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("用户发送未知命令码");
            }
            // 关闭其连接
            Aio.close(channelContext);
        }
        // 获取消息体长度
        final int dataLength = buffer.getInt();
        // 获取剩余报文长度
        final int remainingLength = buffer.remaining();
        // 判断剩余TCP报文还够不够获取消息体
        if (remainingLength < dataLength) {
            buffer.reset();
            return null;
        }
        // 获取消息体
        byte[] b = AIOUtil.getBytesFromByteBuffer(readBuffer, dataLength, 10, channelContext);
        if (b == null) {
            buffer.reset();
            return null;
        }
        // 解码成功
        return new ImPacket(command, b);
    }

    @Override
    public void encode(Packet packet, ChannelContext channelContext) throws AioEncoderException {

        if (packet instanceof ImPacket) {
            // 开始编码
            ImPacket imPacket = (ImPacket) packet;
            // 消息命令码
            TIMCommandType TIMCommandType = imPacket.getTIMCommandType();
            // 消息体
            byte[] data = imPacket.getData();
            // 拿到输入流
            WriteBuffer writeBuffer = channelContext.getWriteBuffer();
            // 写入协议版本号
            writeBuffer.writeByte(timProtocolVersion);
            // 写入协议的特殊标识
            writeBuffer.writeByte(timProtocolMark);
            // 写入命令码
            writeBuffer.writeInt(TIMCommandType.getCode());
            // 写入消息体长度
            writeBuffer.writeInt(data.length);
            // 写入消息体
            writeBuffer.write(data);

        }else {
            throw new AioEncoderException("TIM private TCP protocol encode failed because of the Packet is not ImPacket.");
        }

    }

    @Override
    public ProtocolEnum name() {
        return ProtocolEnum.PRIVATE_TCP;
    }
}
