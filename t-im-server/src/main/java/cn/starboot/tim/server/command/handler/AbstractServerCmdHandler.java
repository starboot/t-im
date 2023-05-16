package cn.starboot.tim.server.command.handler;

import cn.starboot.socket.ChannelContextFilter;
import cn.starboot.tim.common.ImConfig;
import cn.starboot.tim.common.command.handler.AbstractCmdHandler;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.RespPacketProto;
import cn.starboot.tim.server.ImServerChannelContext;
import cn.starboot.tim.server.TIM;
import cn.starboot.tim.server.cache.TIMPersistentHelper;
import cn.starboot.tim.server.intf.ServerTIMProcessor;

/**
 * Created by DELL(mxd) on 2021/12/24 16:45
 */
public abstract class AbstractServerCmdHandler extends AbstractCmdHandler<ImServerChannelContext> {

	protected void sendToId(ImConfig<ServerTIMProcessor, TIMPersistentHelper> imConfig, String toId, ImPacket imPacket) {
		TIM.sendToId(imConfig, toId, imPacket);
	}

	protected void sendToGroup(ImConfig<ServerTIMProcessor, TIMPersistentHelper> imConfig, String toGroupId, ImPacket imPacket) {
		TIM.sendToGroup(imConfig, toGroupId, imPacket);
	}

	protected void sendToGroup(ImConfig<ServerTIMProcessor, TIMPersistentHelper> imConfig, String toGroupId, ImPacket imPacket, ChannelContextFilter channelContextFilter) {
		TIM.sendToGroup(imConfig, toGroupId, imPacket, channelContextFilter);
	}

	protected void setRespPacketImStatus(ImPacket packet, RespPacketProto.RespPacket.ImStatus imStatus) {
		packet.setImStatus(imStatus);
	}

}
