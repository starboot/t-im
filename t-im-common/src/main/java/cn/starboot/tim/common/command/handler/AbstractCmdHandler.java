package cn.starboot.tim.common.command.handler;

import cn.starboot.socket.ChannelContextFilter;
import cn.starboot.socket.core.Aio;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.ImConfig;
import cn.starboot.tim.common.command.ReqServerCommandType;
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
	 * @param reqServerCommandType   协议命令码
	 * @param data             待发送数据
	 */
	protected abstract void send(ImChannelContext imChannelContext, ReqServerCommandType reqServerCommandType, byte[] data);

	protected abstract void sendToId(ImConfig imConfig,String toId, ReqServerCommandType reqServerCommandType, byte[] data);

	protected abstract void sendToGroup(ImConfig imConfig,String toGroupId, ReqServerCommandType reqServerCommandType, byte[] data);

	protected abstract void sendToGroup(ImConfig imConfig,String toGroupId, ReqServerCommandType reqServerCommandType, byte[] data, ChannelContextFilter channelContextFilter);

	protected ImPacket getImPacket(ReqServerCommandType reqServerCommandType, byte[] data) {
		return ImPacket
				.newBuilder()
				.setReqServerCommandType(reqServerCommandType)
				.setData(data)
				.build();
	}
}
