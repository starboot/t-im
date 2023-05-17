package cn.starboot.tim.client.command.handler;

import cn.starboot.tim.client.ImClientChannelContext;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.packet.ImPacket;
import com.google.protobuf.InvalidProtocolBufferException;

public class ClientRespHandler extends AbstractClientCmdHandler {

	@Override
	public TIMCommandType command() {
		return TIMCommandType.COMMAND_REQ_RESP;
	}

	@Override
	public ImPacket handler(ImPacket imPacket, ImClientChannelContext channelContext) throws ImException, InvalidProtocolBufferException {
		return null;
	}
}
