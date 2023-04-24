package cn.starboot.tim.common.command.handler;

import cn.starboot.socket.core.Aio;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.command.ReqCommandType;
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
	 * @param reqCommandType   协议命令码
	 * @param data             待发送数据
	 */
	protected void send(ImChannelContext imChannelContext, ReqCommandType reqCommandType, byte[] data) {
		Aio.send(imChannelContext.getChannelContext(), new ImPacket(reqCommandType, data));
	}
}
