package org.tim.server.command.handler;

import org.tim.common.ImPacket;
import org.tim.common.packets.Command;
import org.tim.common.packets.HeartbeatBody;
import org.tim.common.packets.RespBody;
import org.tim.server.command.AbstractCmdHandler;
import org.tim.server.helper.TIM;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;

/**
 * Created by DELL(mxd) on 2021/12/25 14:08
 */
public class HeartbeatReqHandler extends AbstractCmdHandler {

    @Override
    public Command command() {
        return Command.COMMAND_HEARTBEAT_REQ;
    }

    @Override
    public Packet handler(Packet packet, ChannelContext channelContext) {
        byte heartbeat = -127;  // 0111 1111
        RespBody heartbeatBody = new RespBody(Command.COMMAND_HEARTBEAT_REQ).setCode(200).setData(new HeartbeatBody(heartbeat));
        ImPacket heartbeatPacket = new ImPacket(Command.COMMAND_HEARTBEAT_REQ,heartbeatBody.toByte());
//        TIM.send(channelContext, heartbeatPacket);
        return null;
    }
}
