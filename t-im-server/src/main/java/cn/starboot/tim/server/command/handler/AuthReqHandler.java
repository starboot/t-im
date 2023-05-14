package cn.starboot.tim.server.command.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.AuthPacketProto;
import cn.starboot.tim.common.packet.proto.RespPacketProto;
import cn.starboot.tim.server.ImServerChannelContext;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 授权方式暂未限制
 * Created by DELL(mxd) on 2021/12/25 11:57
 */
public class AuthReqHandler extends AbstractServerCmdHandler {

	private static final Logger log = LoggerFactory.getLogger(AuthReqHandler.class);

	@Override
	public TIMCommandType command() {
		return TIMCommandType.COMMAND_AUTH_REQ;
	}

	@Override
	public ImPacket handler(ImPacket imPacket, ImServerChannelContext imChannelContext) throws InvalidProtocolBufferException {

		AuthPacketProto.AuthPacket authPacket = AuthPacketProto.AuthPacket.parseFrom(imPacket.getData());
		if (ObjectUtil.isEmpty(authPacket)) {
			log.error("消息包格式化出错");
			return null;
		}
		imPacket.setTIMCommandType(TIMCommandType.COMMAND_AUTH_RESP);
		setRespPacketImStatus(imPacket,
				verify(StrUtil.isNotBlank(authPacket.getUserId()),
						StrUtil.isNotBlank(authPacket.getToken()),
						imChannelContext
								.getConfig()
								.getProcessor()
								.handleAuthPacket(imChannelContext, authPacket)) ?
						RespPacketProto.RespPacket.ImStatus.AUTH_SUCCESS :
						RespPacketProto.RespPacket.ImStatus.AUTH_FAILED);

		return imChannelContext.getConfig().getProcessor().beforeSend(imChannelContext, imPacket) ? imPacket : null;
	}

}
