package cn.starboot.tim.server.command.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.starboot.socket.enums.CloseCode;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.ClosePacketProto;
import cn.starboot.tim.server.ImServerChannelContext;
import cn.starboot.tim.server.TIM;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by DELL(mxd) on 2021/12/25 14:48
 */
public class CloseReqHandler extends AbstractServerCmdHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CloseReqHandler.class);

    @Override
    public TIMCommandType command() {
        return TIMCommandType.COMMAND_CLOSE_REQ;
    }

	@Override
    public ImPacket handler(ImPacket imPacket, ImServerChannelContext imChannelContext) throws InvalidProtocolBufferException {
        ClosePacketProto.ClosePacket closePacket = ClosePacketProto.ClosePacket.parseFrom(imPacket.getData());
        if (ObjectUtil.isEmpty(closePacket) || StrUtil.isBlank(closePacket.getUserId())) {
			LOGGER.error("用户发送异常关闭请求，将其强行断开");
            TIM.remove(imChannelContext);
        }
        if (imChannelContext.getConfig().getProcessor().handleClosePacket(imChannelContext, closePacket)) {
			TIM.remove(imChannelContext, CloseCode.NO_CODE);
		}
        return null;
    }
}
