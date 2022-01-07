package org.tim.server.convert;

import org.tim.common.ImPacket;
import org.tio.core.ChannelContext;
import org.tio.core.PacketConverter;
import org.tio.core.intf.Packet;
import org.tio.websocket.common.Opcode;
import org.tio.websocket.common.WsResponse;

import java.nio.charset.StandardCharsets;

/**
 * Created by DELL(mxd) on 2021/12/23 20:43
 */
public class websocketPackageConvert implements PacketConverter {

    /**
     * @param packet .
     * @param channelContext 要发往的channelContext
     * @return .
     * @author tanyaowu
     */
    @Override
    public Packet convert(Packet packet, ChannelContext channelContext) {
        if (packet instanceof ImPacket){
            ImPacket p = (ImPacket) packet;
            WsResponse wsResponse = new WsResponse();
            wsResponse.setWsOpcode(Opcode.TEXT);
            wsResponse.setBody(p.getBody());
            wsResponse.setWsBodyText(new String(p.getBody(), StandardCharsets.UTF_8));
            return wsResponse;
        }
        return packet;
    }
}
