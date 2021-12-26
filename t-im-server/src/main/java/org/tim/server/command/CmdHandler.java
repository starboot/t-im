package org.tim.server.command;

import org.tim.common.exception.ImException;
import org.tim.common.packets.Command;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;

/**
 * Created by DELL(mxd) on 2021/12/24 16:40
 */
public interface CmdHandler {
    /**
     * 主键：判断各类socket请求的指令
     * @return 主键对象
     */
    Command command();
    /**
     * 处理Cmd命令
     * @param packet IM消息包
     * @param channelContext 通道上下文
     * @return .
     */
    Packet handler(Packet packet, ChannelContext channelContext)  throws ImException;
}
