package cn.starboot.tim.common.command.handler;

import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.factory.ImPacketFactory;
import org.checkerframework.checker.units.qual.C;

/**
 * 抽象命令处理器
 * Created by DELL(mxd) on 2022/9/6 13:04
 */
public abstract class AbstractCmdHandler<C extends ImChannelContext> implements CmdHandler<C> {

	private static final ImPacketFactory imPacketFactory = (timCommandType, data) -> ImPacket
			.newBuilder()
			.setTIMCommandType(timCommandType)
			.setData(data)
			.build();
	/**
	 * 命令处理器自带发送方法
	 *
	 * @param imChannelContext 用户上下文信息
	 * @param TIMCommandType   协议命令码
	 * @param data             待发送数据
	 */
//	protected abstract void send(ImChannelContext imChannelContext, TIMCommandType TIMCommandType, byte[] data);

	protected ImPacket getImPacket(TIMCommandType timCommandType, byte[] data) {
		return imPacketFactory.createImPacket(timCommandType, data);
	}

	protected ImPacket getImPacket(String req, String resp, TIMCommandType timCommandType, byte[] data) {
		return imPacketFactory.createImPacket(timCommandType, data);
	}
}
