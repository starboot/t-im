package cn.starboot.tim.server.command.handler;

import cn.starboot.socket.ChannelContextFilter;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.ImConfig;
import cn.starboot.tim.common.command.TIMCommandType;
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
	public abstract TIMCommandType command();

	@Override
	protected void send(ImChannelContext imChannelContext, TIMCommandType TIMCommandType, byte[] data) {
		TIM.send(imChannelContext, getImPacket(TIMCommandType, data));
	}

	@Override
	protected void sendToId(ImConfig imConfig, String toId, TIMCommandType TIMCommandType, byte[] data) {
		TIM.sendToId(imConfig, toId, getImPacket(TIMCommandType, data));
	}

	@Override
	protected void sendToGroup(ImConfig imConfig, String toGroupId, TIMCommandType TIMCommandType, byte[] data) {
		TIM.sendToGroup(imConfig, toGroupId, getImPacket(TIMCommandType, data));
	}

	@Override
	protected void sendToGroup(ImConfig imConfig, String toGroupId, TIMCommandType TIMCommandType, byte[] data, ChannelContextFilter channelContextFilter) {
		TIM.sendToGroup(imConfig, toGroupId, getImPacket(TIMCommandType, data), channelContextFilter);
	}
}
