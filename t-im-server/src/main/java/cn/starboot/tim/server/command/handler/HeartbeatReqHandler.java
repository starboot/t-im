package cn.starboot.tim.server.command.handler;

import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.command.ReqServerCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.HeartPacketProto;
import com.google.protobuf.ByteString;

/**
 * 虽然aio-socket在TCP层面带有心跳插件
 * 但是如果客户端未使用aio-socket的client端，则需要定时发送心跳请求
 *
 * Created by DELL(mxd) on 2021/12/25 14:08
 */
public class HeartbeatReqHandler extends AbstractServerCmdHandler {

    @Override
    public ReqServerCommandType command() {
        return ReqServerCommandType.COMMAND_HEART_REQ;
    }

    @Override
    public ImPacket handler(ImPacket imPacket, ImChannelContext channelContext) {
        HeartPacketProto.HeartPacket.Builder builder = HeartPacketProto.HeartPacket.newBuilder();
        HeartPacketProto.HeartPacket build = builder.setHeartByte(ByteString.copyFrom(new byte[]{(byte) 0xff}))
                .build();
        imPacket.setReqServerCommandType(ReqServerCommandType.COMMAND_HEART_REQ)
                .setData(build.toByteArray());
        // 将build1发送给客户端
//        TIM.send(channelContext, heartbeatPacket);
        return null;
    }
}
