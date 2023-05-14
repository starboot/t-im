package cn.starboot.tim.server.command.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.UserPacketProto;
import cn.starboot.tim.server.ImServerChannelContext;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取在线用户集合
 * Created by DELL(mxd) on 2021/12/25 16:31
 */
public class UserReqHandler extends AbstractServerCmdHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserReqHandler.class);

	@Override
	public TIMCommandType command() {
		return TIMCommandType.COMMAND_USERS_REQ;
	}

	@Override
	public ImPacket handler(ImPacket imPacket, ImServerChannelContext imChannelContext) throws InvalidProtocolBufferException {

		UserPacketProto.UserPacket userPacket = UserPacketProto.UserPacket.parseFrom(imPacket.getData());
		if (ObjectUtil.isEmpty(userPacket)) {
			LOGGER.error("消息包格式化出错");
			return null;
		}
		imPacket.setTIMCommandType(TIMCommandType.COMMAND_USERS_RESP);
		return imPacket;
	}


}
