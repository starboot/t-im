package cn.starboot.tim.common.command.handler;

import cn.starboot.socket.ChannelContextFilter;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.ImConfig;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.packet.ImPacket;

/**
 * 抽象命令处理器
 * Created by DELL(mxd) on 2022/9/6 13:04
 */
public abstract class AbstractCmdHandler implements CmdHandler {



	/**
	 * 命令处理器自带发送方法
	 *
	 * @param imChannelContext 用户上下文信息
	 * @param TIMCommandType   协议命令码
	 * @param data             待发送数据
	 */
	protected abstract void send(ImChannelContext imChannelContext, TIMCommandType TIMCommandType, byte[] data);

	protected abstract void sendToId(ImConfig imConfig, String toId, TIMCommandType TIMCommandType, byte[] data);

	protected abstract void sendToGroup(ImConfig imConfig, String toGroupId, TIMCommandType TIMCommandType, byte[] data);

	protected abstract void sendToGroup(ImConfig imConfig, String toGroupId, TIMCommandType TIMCommandType, byte[] data, ChannelContextFilter channelContextFilter);

	protected ImPacket getImPacket(TIMCommandType TIMCommandType, byte[] data) {
		return ImPacket
				.newBuilder()
				.setTIMCommandType(TIMCommandType)
				.setData(data)
				.build();
	}
}
