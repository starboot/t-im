package cn.starboot.tim.server.command.handler;

import cn.starboot.socket.ChannelContextFilter;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.ImConfig;
import cn.starboot.tim.common.command.ReqServerCommandType;
import cn.starboot.tim.common.command.handler.AbstractCmdHandler;
import cn.starboot.tim.server.TIM;

/**
 * Created by DELL(mxd) on 2021/12/24 16:45
 */
public abstract class AbstractServerCmdHandler extends AbstractCmdHandler {


	/**
	 * 主键：判断各类socket请求的指令
	 * @return 主键对象
	 */
	public abstract ReqServerCommandType command();

	@Override
	protected void send(ImChannelContext imChannelContext, ReqServerCommandType reqServerCommandType, byte[] data) {
		TIM.send(imChannelContext, getImPacket(reqServerCommandType, data));
	}

	@Override
	protected void sendToId(ImConfig imConfig, String toId, ReqServerCommandType reqServerCommandType, byte[] data) {
		TIM.sendToId(imConfig, toId, getImPacket(reqServerCommandType, data));
	}

	@Override
	protected void sendToGroup(ImConfig imConfig, String toGroupId, ReqServerCommandType reqServerCommandType, byte[] data) {
		TIM.sendToGroup(imConfig, toGroupId, getImPacket(reqServerCommandType, data));
	}

	@Override
	protected void sendToGroup(ImConfig imConfig, String toGroupId, ReqServerCommandType reqServerCommandType, byte[] data, ChannelContextFilter channelContextFilter) {
		TIM.sendToGroup(imConfig, toGroupId, getImPacket(reqServerCommandType, data), channelContextFilter);
	}
}
