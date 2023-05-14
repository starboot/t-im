package cn.starboot.tim.server.command.handler;

import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.server.ImServerChannelContext;
import com.google.protobuf.InvalidProtocolBufferException;

public class RespServerHandler extends AbstractServerCmdHandler {

	@Override
	public TIMCommandType command() {
		return TIMCommandType.COMMAND_REQ_RESP;
	}

	@Override
	public ImPacket handler(ImPacket imPacket, ImServerChannelContext imChannelContext) throws ImException, InvalidProtocolBufferException {
		return null;
	}
}
