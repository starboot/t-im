package cn.starboot.tim.common.codec;

import cn.starboot.socket.Packet;
import cn.starboot.socket.codec.string.StringPacket;
import cn.starboot.socket.core.Aio;
import cn.starboot.socket.core.ChannelContext;
import cn.starboot.socket.core.WriteBuffer;
import cn.starboot.socket.enums.ProtocolEnum;
import cn.starboot.socket.exception.AioDecoderException;
import cn.starboot.socket.exception.AioEncoderException;
import cn.starboot.socket.intf.AioHandler;
import cn.starboot.socket.utils.pool.memory.MemoryUnit;
import cn.starboot.tim.common.ImConst;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.util.TIMLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * 使用aio-socket，对私有TCP进行解编码
 * 对标websocket(对标不代表要替代，表示所属层面相同)
 *
 * Created by DELL(mxd) on 2021/12/24 20:52
 */
public abstract class PrivateTcpProtocol implements AioHandler, ImConst {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrivateTcpProtocol.class);

    // TIM系统定制私有TCP通讯协议
    private static PrivateTcpProtocol privateTcpProtocol;

    // 协议版本号
    private static final byte timProtocolVersion = (byte) 0x01;

    // 此消息为t-im系统的标识
    private static final byte timProtocolMark = (byte) 0xff;

	// 定制私有协议最少比特位: 1(版本号:version) + 1(系统标识:mark)
	private static final int minLength = 2;

	private final TIMPacketProtocol<?> timPacketProtocol;

    // 此对象不让用户自己实例化
    protected PrivateTcpProtocol(TIMPacketProtocol<?> timPacketProtocol) {
    	this.timPacketProtocol = timPacketProtocol;
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
			TIMLogUtil.debug(LOGGER, "client send error packet");
			Aio.close(channelContext);
            return null;
        }
		ImPacket imPacket = timPacketProtocol.decode(readBuffer, channelContext);
        if (imPacket == null) {
        	buffer.reset();
        	return null;
		}
		// 解码成功
        return imPacket;
    }

    @Override
    public void encode(Packet packet, ChannelContext channelContext) throws AioEncoderException {
        if (packet instanceof ImPacket) {
//            // 拿到输入流
            WriteBuffer writeBuffer = channelContext.getWriteBuffer();
//            // 写入协议版本号
            writeBuffer.writeByte(timProtocolVersion);
//            // 写入协议的特殊标识
            writeBuffer.writeByte(timProtocolMark);
//			// t-im协议编码
			timPacketProtocol.encode((ImPacket) packet, channelContext);
		}else {
            throw new AioEncoderException("TIM private TCP protocol encode failed because of the Packet is not ImPacket.");
        }
    }

    @Override
    public ProtocolEnum name() {
        return ProtocolEnum.PRIVATE_TCP;
    }
}
