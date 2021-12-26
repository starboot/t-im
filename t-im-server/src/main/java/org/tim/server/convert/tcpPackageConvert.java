package org.tim.server.convert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.common.ImPacket;
import org.tim.common.packets.Command;
import org.tio.core.ChannelContext;
import org.tio.core.PacketConverter;
import org.tio.core.intf.Packet;
import org.tio.websocket.common.WsResponse;

/**
 * Created by DELL(mxd) on 2021/12/24 13:44
 */
public class tcpPackageConvert implements PacketConverter {

    private static final Logger log = LoggerFactory.getLogger(tcpPackageConvert.class);
    /**
     *
     * @param packet 未知包类型
     * @param channelContext 通道
     * @return 用户所在协议下的消息包
     */
    @Override
    public Packet convert(Packet packet, ChannelContext channelContext) {
        log.debug("进行WS包转换TCP包");
        if (packet instanceof WsResponse){
            WsResponse p = (WsResponse) packet;
            ImPacket tcpPacket = new ImPacket();
            tcpPacket.setCommand(Command.valueOf(11));
            tcpPacket.setBody(p.getBody());
            return tcpPacket;
        }
        return packet;
    }
}
