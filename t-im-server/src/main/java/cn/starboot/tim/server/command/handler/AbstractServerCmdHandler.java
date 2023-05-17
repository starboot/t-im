package cn.starboot.tim.server.command.handler;

import cn.starboot.socket.ChannelContextFilter;
import cn.starboot.tim.common.command.handler.AbstractCmdHandler;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.RespPacketProto;
import cn.starboot.tim.server.ImServerChannelContext;
import cn.starboot.tim.server.ImServerConfig;
import cn.starboot.tim.server.TIMServer;
import cn.starboot.tim.server.intf.ServerTIMProcessor;

/**
 * Created by DELL(mxd) on 2021/12/24 16:45
 */
public abstract class AbstractServerCmdHandler extends AbstractCmdHandler<ImServerChannelContext, ImServerConfig, ServerTIMProcessor> {

	protected void sendToId(ImServerConfig imConfig, String toId, ImPacket imPacket) {
		TIMServer.sendToId(imConfig, toId, imPacket);
	}

	protected void sendToGroup(ImServerConfig imConfig, String toGroupId, ImPacket imPacket) {
		TIMServer.sendToGroup(imConfig, toGroupId, imPacket);
	}

	protected void sendToGroup(ImServerConfig imConfig, String toGroupId, ImPacket imPacket, ChannelContextFilter channelContextFilter) {
		TIMServer.sendToGroup(imConfig, toGroupId, imPacket, channelContextFilter);
	}

	protected void setRespPacketImStatus(ImPacket packet, RespPacketProto.RespPacket.ImStatus imStatus) {
		packet.setImStatus(imStatus);
	}

}
