package cn.starboot.tim.server.command.handler;

import cn.starboot.socket.ChannelContextFilter;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.ImConfig;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.command.handler.AbstractCmdHandler;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.RespPacketProto;
import cn.starboot.tim.server.ImServerChannelContext;
import cn.starboot.tim.server.TIM;

/**
 * Created by DELL(mxd) on 2021/12/24 16:45
 */
public abstract class AbstractServerCmdHandler extends AbstractCmdHandler<ImServerChannelContext> {

//	@Override
	protected void send(ImChannelContext imChannelContext, TIMCommandType TIMCommandType, byte[] data) {
		TIM.send(imChannelContext, getImPacket(TIMCommandType, data));
	}

	protected void sendToId(ImConfig imConfig, String toId, ImPacket imPacket) {
		TIM.sendToId(imConfig, toId, imPacket);
	}

	protected void sendToGroup(ImConfig imConfig, String toGroupId, ImPacket imPacket) {
		TIM.sendToGroup(imConfig, toGroupId, imPacket);
	}

	protected void sendToGroup(ImConfig imConfig, String toGroupId, ImPacket imPacket, ChannelContextFilter channelContextFilter) {
		TIM.sendToGroup(imConfig, toGroupId, imPacket, channelContextFilter);
	}

	protected void setRespPacketImStatus(ImPacket packet, RespPacketProto.RespPacket.ImStatus imStatus) {
		packet.setData(RespPacketProto.RespPacket.newBuilder()
				.setImStatus(imStatus)
				.build()
				.toByteArray());
	}
}
