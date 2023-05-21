package cn.starboot.tim.common.codec;

import cn.starboot.socket.Packet;
import cn.starboot.socket.core.Aio;
import cn.starboot.socket.core.ChannelContext;
import cn.starboot.socket.core.WriteBuffer;
import cn.starboot.socket.exception.AioEncoderException;
import cn.starboot.socket.utils.AIOUtil;
import cn.starboot.socket.utils.pool.memory.MemoryUnit;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.ImConfig;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.exception.ImDecodeException;
import cn.starboot.tim.common.exception.ImEncodeException;
import cn.starboot.tim.common.intf.TIMProcessor;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.RespPacketProto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * t-im 专用IM协议，属于上层协议，且基于websocket、HTTP、mqtt、私有TCP等
 *
 * @author MDong
 */
public abstract class TIMPacketProtocol<T extends ImChannelContext<?>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(TIMPacketProtocol.class);

	// 1(命令码:commandType) + 1(imStatus标志位: 1存在, 0: 不存在) + 4(imStatus, 不存在时就没有这4字节) + 4(消息体长度:length) + n
	private static final int minLength = 6;

	public ImPacket decode(MemoryUnit readBuffer, ChannelContext channelContext) throws ImDecodeException {
		ByteBuffer buffer = readBuffer.buffer();
		// 获取TCP报文的长度
		int remaining = buffer.remaining();
		// 定制TCP消息的最短长度为 minLength
		if (remaining < minLength) {
			return null;
		}
		// 获取命令码数字索引
		final byte commandType = buffer.get();
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
			return null;
		}
		// 取IM状态码
		int imStatusCode = buffer.get() > 0 ? buffer.getInt() : 0;
		// 获取消息体长度
		final int dataLength = buffer.getInt();
		// 获取剩余报文长度
		final int remainingLength = buffer.remaining();
		// 判断剩余TCP报文还够不够获取消息体
		if (remainingLength < dataLength) {
			return null;
		}
		byte[] b = null;
		if (dataLength > 0) {
			// 获取消息体
			b = AIOUtil.getBytesFromByteBuffer(readBuffer, dataLength, 10, channelContext);
			if (b == null) {
				return null;
			}
		}
		// 解码成功
		return ImPacket.newBuilder().setTIMCommandType(command).setImStatus(RespPacketProto.RespPacket.ImStatus.forNumber(imStatusCode)).setData(b).build();
	}

	public void encode(ImPacket imPacket, ChannelContext channelContext) throws AioEncoderException {
		// 消息命令码
		TIMCommandType timCommandType = imPacket.getTIMCommandType();
		// 消息体
		byte[] data = imPacket.getData();
		// 状态码
		RespPacketProto.RespPacket.ImStatus imStatus = imPacket.getImStatus();
		// 拿到输入流
		WriteBuffer writeBuffer = channelContext.getWriteBuffer();
		if (Objects.isNull(timCommandType)) {
			throw new ImEncodeException("TIM private TCP protocol encode failed because of the TIMCommandType is not defined.");
		}
		// 写入命令码
		writeBuffer.writeByte(timCommandType.getCode());
		// 写入状态码
		if (Objects.isNull(imStatus)) {
			writeBuffer.write(0);
		} else {
			writeBuffer.write(1);
			writeBuffer.writeInt(imPacket.getImStatus().getNumber());
		}
		// 写入消息体长度
		writeBuffer.writeInt(data.length);
		// 写入消息体
		if (data.length > 0) {
			writeBuffer.write(data);
		}
	}

	public abstract ImPacket handle(T imChannelContext, ImPacket imPacket);
}
