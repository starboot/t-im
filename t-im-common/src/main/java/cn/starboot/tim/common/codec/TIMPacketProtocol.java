package cn.starboot.tim.common.codec;

import cn.starboot.socket.core.Aio;
import cn.starboot.socket.core.ChannelContext;
import cn.starboot.socket.core.WriteBuffer;
import cn.starboot.socket.exception.AioEncoderException;
import cn.starboot.socket.utils.AIOUtil;
import cn.starboot.socket.utils.pool.memory.MemoryUnit;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.exception.ImDecodeException;
import cn.starboot.tim.common.exception.ImEncodeException;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.RespPacketProto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.Set;

/**
 * t-im 专用IM协议，属于上层协议，且基于websocket、HTTP、mqtt、私有TCP等
 *
 * @author MDong
 */
public abstract class TIMPacketProtocol<T extends ImChannelContext<?>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(TIMPacketProtocol.class);

	// 1(命令码:commandType) + 1(imStatus标志位: 1存在, 0: 不存在) + 4(imStatus, 不存在时就没有这4字节) + 1(同步标志位) + 4(消息体长度:length) + n
	private static final int minLength = 7;

	private static final byte synMark_not_exist = (byte) 0x00;

	private static final byte synMark_only_req = (byte) 0x01;

	private static final byte synMark_only_resp = (byte) 0x02;

	private static final byte synMark_req_resp = (byte) 0x03;

	private static final byte data_not_exist  = (byte) 0x00;

	private static final byte data_exist  = (byte) 0x01;

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
		// 获取同部位
		Integer synReq = null;
		Integer synResp = null;
		switch (buffer.get()) {
			case synMark_not_exist: break;
			case synMark_only_req: {
				synReq = buffer.getInt();
				break;
			}
			case synMark_only_resp: {
				synResp = buffer.getInt();
				break;
			}
			case synMark_req_resp: {
				synReq = buffer.getInt();
				synResp = buffer.getInt();
				break;
			}
		}

		byte[] b = null;
		if (buffer.get() == data_exist) {
			// 获取消息体长度
			final int dataLength = buffer.getInt();
			// 获取剩余报文长度
			final int remainingLength = buffer.remaining();
			// 判断剩余TCP报文还够不够获取消息体
			if (remainingLength < dataLength) {
				return null;
			}
			if (dataLength > 0) {
				// 获取消息体
				b = AIOUtil.getBytesFromByteBuffer(readBuffer, dataLength, 10, channelContext);
				if (b == null) {
					return null;
				}
			}
		}
		// 解码成功
		return ImPacket.newBuilder().setReq(synReq).setResp(synResp).setTIMCommandType(command).setData(b).build();
	}

	public void encode(ImPacket imPacket, ChannelContext channelContext) throws AioEncoderException {
		// 消息命令码
		TIMCommandType timCommandType = imPacket.getTIMCommandType();
		// 消息体
		byte[] data = imPacket.getData();
		// 拿到输入流
		WriteBuffer writeBuffer = channelContext.getWriteBuffer();
		if (Objects.isNull(timCommandType)) {
			throw new ImEncodeException("TIM private TCP protocol encode failed because of the TIMCommandType is not defined.");
		}
		// 写入命令码
		writeBuffer.writeByte(timCommandType.getCode());
		// 同步位
		Integer req = imPacket.getReq();
		Integer resp = imPacket.getResp();
		if (Objects.isNull(req) && Objects.isNull(resp)) {
			writeBuffer.write(synMark_not_exist);
		} else if (Objects.isNull(req)) {
			writeBuffer.write(synMark_only_resp);
			writeBuffer.writeInt(resp);
		} else if (Objects.isNull(resp)) {
			writeBuffer.write(synMark_only_req);
			writeBuffer.writeInt(req);
		} else {
			writeBuffer.write(synMark_req_resp);
			writeBuffer.writeInt(req);
			writeBuffer.writeInt(resp);
		}
		if (Objects.isNull(data)) {
			writeBuffer.write(data_not_exist);
			return;
		}
		writeBuffer.write(data_exist);
		// 写入消息体长度
		writeBuffer.writeInt(data.length);
		// 写入消息体
		if (data.length > 0) {
			writeBuffer.write(data);
		}
	}

	public abstract ImPacket handle(T imChannelContext, ImPacket imPacket);

	protected enum ImPacketEnum {
		// 重复消息包
		REPEAT,

		// 非法消息包
		INVALID,

		// 正常消息包
		VALID
	}

	/**
	 * 预处理
	 */
	protected ImPacketEnum preHandle(ImChannelContext<?> imChannelContext, ImPacket imPacket) {
		Integer req = imPacket.getReq();
		// 启用ACK
		if (imChannelContext.getConfig().isAckPlugin() && req != null) {
			Integer reqInteger = imChannelContext.getReqInteger();

			Integer minSynMessagePoolNum = imChannelContext.getMinSynMessagePoolNum();
			Set<Integer> synMessagePool = imChannelContext.getSynMessagePool();
			// 收到理论顺序消息包
			if (reqInteger.equals(req)) {
				return ImPacketEnum.VALID;
			}
			// 收到小于理论顺序包裹，可能存在重复投递或者丢失的消息包
			if (reqInteger > req) {
				if (req < minSynMessagePoolNum) {
					return ImPacketEnum.REPEAT;
				}

				// imChannelContext.getMinMissMessage() <= imPacket.getReq() < imChannelContext.getReqInteger()
				// 此时可能重复，可能是丢失的包裹
				if (synMessagePool.contains(req)) {
					// 确定为丢失的包裹
					synMessagePool.remove(req);
					return ImPacketEnum.VALID;
				} else
					return ImPacketEnum.REPEAT;
			}
			// 收到大于理论顺序的消息包（存在丢包现象，先处理，将丢失的ack进行保存）
			if (req - reqInteger + 1 > imChannelContext.getConfig().getMaximumInterval() - synMessagePool.size()) {
				// 收到的消息包 丢失严重。
				return ImPacketEnum.INVALID;
			} else {
				for (int i = reqInteger; i < req; i++) {
					synMessagePool.add(i);
				}
				return ImPacketEnum.VALID;
			}
		}
		return ImPacketEnum.VALID;
	}
}
