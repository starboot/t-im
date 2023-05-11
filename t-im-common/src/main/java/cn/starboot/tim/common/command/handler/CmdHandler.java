package cn.starboot.tim.common.command.handler;

import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.command.ReqServerCommandType;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.packet.ImPacket;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * Created by DELL(mxd) on 2021/12/24 16:40
 */
public interface CmdHandler {

    /**
     * 主键：判断各类socket请求的指令
     * @return 主键对象
     */
    ReqServerCommandType command();

    /**
     * 处理Cmd命令
     * @param imPacket          IM消息包
     * @param imChannelContext  通道上下文
     * @return .
     */
    ImPacket handler(ImPacket imPacket, ImChannelContext imChannelContext) throws ImException, InvalidProtocolBufferException;
}
