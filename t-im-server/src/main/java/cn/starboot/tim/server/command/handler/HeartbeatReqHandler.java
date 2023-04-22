package cn.starboot.tim.server.command.handler;

import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.packet.CommandType;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.HeartPacketProto;
import cn.starboot.tim.server.command.ServerAbstractCmdHandler;
import com.google.protobuf.ByteString;

/**
 * Created by DELL(mxd) on 2021/12/25 14:08
 */
public class HeartbeatReqHandler extends ServerAbstractCmdHandler {

    @Override
    public CommandType command() {
        return CommandType.COMMAND_HEART;
    }

    @Override
    public ImPacket handler(ImPacket imPacket, ImChannelContext channelContext) {
        HeartPacketProto.HeartPacket.Builder builder = HeartPacketProto.HeartPacket.newBuilder();
        HeartPacketProto.HeartPacket build = builder.setHeartByte(ByteString.copyFrom(new byte[]{(byte) 0xff}))
                .build();
        imPacket.setCommandType(CommandType.COMMAND_HEART)
                .setData(build.toByteArray());
        // 将build1发送给客户端
//        TIM.send(channelContext, heartbeatPacket);
        return null;
    }
}
