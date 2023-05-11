package cn.starboot.tim.client.command.handler;

import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.command.ReqServerCommandType;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.packet.ImPacket;
import com.google.protobuf.InvalidProtocolBufferException;

public class ClientRespHandler extends AbstractClientCmdHandler {

	@Override
	public ReqServerCommandType command() {
		return ReqServerCommandType.COMMAND_REQ_RESP;
	}

	@Override
	public ImPacket handler(ImPacket imPacket, ImChannelContext channelContext) throws ImException, InvalidProtocolBufferException {
		return null;
	}
}
