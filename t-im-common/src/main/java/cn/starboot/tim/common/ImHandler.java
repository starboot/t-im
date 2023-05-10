package cn.starboot.tim.common;

import cn.starboot.socket.enums.StateMachineEnum;
import cn.starboot.socket.utils.pool.memory.MemoryUnit;
import cn.starboot.tim.common.exception.ImDecodeException;
import cn.starboot.tim.common.exception.ImEncodeException;
import cn.starboot.tim.common.packet.ImPacket;

public interface ImHandler {

	/**
	 * 消息处理回调方法
	 *
	 * @param imChannelContext 用户上下文
	 * @param packet           消息包
	 * @return                 返回Packet消息包
	 */
	ImPacket handle(ImChannelContext imChannelContext, ImPacket packet);

	/**
	 * 解码回调方法
	 *
	 * @param readBuffer              读到的buffer流
	 * @param imChannelContext        用户上下文
	 * @return                        返回Packet消息包
	 * @throws ImDecodeException      解码异常
	 */
	ImPacket decode(final MemoryUnit readBuffer, ImChannelContext imChannelContext) throws ImDecodeException;

	/**
	 * 编码回调方法
	 *
	 * @param packet           需要编码消息包
	 * @param imChannelContext 用户上下文
	 */
	void encode(ImPacket packet, ImChannelContext imChannelContext) throws ImEncodeException;

	/**
	 * 状态机事件,当枚举事件发生时由框架触发该方法
	 *
	 * @param imChannelContext  本次触发状态机的ChannelContext对象
	 * @param stateMachineEnum  状态枚举
	 * @param throwable         异常对象，如果存在的话
	 * @see StateMachineEnum    状态机枚举
	 */
	default void stateEvent(ImChannelContext imChannelContext, StateMachineEnum stateMachineEnum, Throwable throwable) {
		if (stateMachineEnum == StateMachineEnum.DECODE_EXCEPTION || stateMachineEnum == StateMachineEnum.PROCESS_EXCEPTION) {
			throwable.printStackTrace();
		}
	}
}
