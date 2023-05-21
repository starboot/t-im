package cn.starboot.tim.common.codec;

import cn.starboot.socket.Packet;
import cn.starboot.socket.core.Aio;
import cn.starboot.socket.core.ChannelContext;
import cn.starboot.socket.utils.AIOUtil;
import cn.starboot.socket.utils.pool.memory.MemoryUnit;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.exception.ImDecodeException;
import cn.starboot.tim.common.exception.ImEncodeException;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.RespPacketProto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * t-im 专用IM协议，属于上层协议，且基于websocket、HTTP、mqtt、私有TCP等
 *
 * @author MDong
 */
public abstract class TIMPacketProtocol {

	private static final Logger LOGGER = LoggerFactory.getLogger(TIMPacketProtocol.class);

	public ImPacket decode(MemoryUnit readBuffer, ChannelContext channelContext) throws ImDecodeException {
		ByteBuffer buffer = readBuffer.buffer();
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
		}
		// 取IM状态码
		int imStatusCode = buffer.get() > 0 ? buffer.getInt() : 0;
		// 获取消息体长度
		final int dataLength = buffer.getInt();
		// 获取剩余报文长度
		final int remainingLength = buffer.remaining();
		// 判断剩余TCP报文还够不够获取消息体
		if (remainingLength < dataLength) {
			buffer.reset();
			return null;
		}
		byte[] b = null;
		if (dataLength > 0) {
			// 获取消息体
			b = AIOUtil.getBytesFromByteBuffer(readBuffer, dataLength, 10, channelContext);
			if (b == null) {
				buffer.reset();
				return null;
			}
		}
		// 解码成功
		return ImPacket.newBuilder().setTIMCommandType(command).setImStatus(RespPacketProto.RespPacket.ImStatus.forNumber(imStatusCode)).setData(b).build();
	}

	public void encode(ImPacket imPacket, ChannelContext channelContext) throws ImEncodeException {

	}
}
