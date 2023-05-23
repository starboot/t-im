package cn.starboot.tim.client.command.handler;

import cn.starboot.tim.client.ImClientChannelContext;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.RespPacketProto;
import com.google.protobuf.InvalidProtocolBufferException;

public class ClientRespHandler extends AbstractClientCmdHandler {

	@Override
	public TIMCommandType command() {
		return TIMCommandType.COMMAND_REQ_RESP;
	}

	@Override
	public ImPacket handler(ImPacket imPacket, ImClientChannelContext channelContext) throws ImException, InvalidProtocolBufferException {
		RespPacketProto.RespPacket respPacket = RespPacketProto.RespPacket.parseFrom(imPacket.getData());
		if (respPacket == null) {
			System.out.println("错误");
			return null;
		}
		System.out.println(respPacket.toString());

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
