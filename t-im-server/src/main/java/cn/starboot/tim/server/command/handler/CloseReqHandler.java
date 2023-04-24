package cn.starboot.tim.server.command.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.packet.ReqCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.ClosePacketProto;
import cn.starboot.tim.server.command.ServerAbstractCmdHandler;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by DELL(mxd) on 2021/12/25 14:48
 */
public class CloseReqHandler extends ServerAbstractCmdHandler {

    private static final Logger log = LoggerFactory.getLogger(CloseReqHandler.class);

    @Override
    public ReqCommandType command() {
        return ReqCommandType.COMMAND_CLOSE_REQ;
    }

    @Override
    public ImPacket handler(ImPacket imPacket, ImChannelContext channelContext) throws InvalidProtocolBufferException {
        ClosePacketProto.ClosePacket closePacket = ClosePacketProto.ClosePacket.parseFrom(imPacket.getData());
        if (ObjectUtil.isEmpty(closePacket) || StrUtil.isEmpty(closePacket.getUserId())) {
            log.error("用户发送异常关闭请求，将其强行断开");
        }
        // 执行断开操作
        return null;
    }
}
