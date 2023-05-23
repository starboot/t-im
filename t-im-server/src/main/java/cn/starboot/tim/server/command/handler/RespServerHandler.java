package cn.starboot.tim.server.command.handler;

import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.RespPacketProto;
import cn.starboot.tim.common.util.TIMLogUtil;
import cn.starboot.tim.server.ImServerChannelContext;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RespServerHandler extends AbstractServerCmdHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(RespServerHandler.class);

	@Override
	public TIMCommandType command() {
		return TIMCommandType.COMMAND_REQ_RESP;
	}

	@Override
	public ImPacket handler(ImPacket imPacket, ImServerChannelContext imChannelContext) throws ImException, InvalidProtocolBufferException {
		RespPacketProto.RespPacket respPacket = RespPacketProto.RespPacket.parseFrom(imPacket.getData());
		if (respPacket == null) {
			TIMLogUtil.error(LOGGER, "RespServerHandler: message formatting error");
			return null;
		}
		switch (getTIMCommandTypeFromCode((byte) respPacket.getCode())) {
			case COMMAND_CHAT_RESP:
				System.out.println("聊天相应");
				break;
			case COMMAND_AUTH_RESP:
				System.out.println("鉴权响应");
				break;
			default:
				System.out.println("命令码错误");
				break;
		}


		return null;
	}

	private TIMCommandType getTIMCommandTypeFromCode (byte code) {
		return TIMCommandType.getCommandTypeByCode(code);
	}
}
