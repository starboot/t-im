package cn.starboot.tim.server.command.handler;

import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.packet.CommandType;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.server.command.ServerAbstractCmdHandler;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 通过反射获取当前类的对象实例，所以下面会有 Class 'ACKReqHandler' is never used 警告
 *
 * Created by DELL(mxd) on 2022/9/6 12:39
 */
public class ACKReqHandler extends ServerAbstractCmdHandler {

    private static final Logger log = LoggerFactory.getLogger(ACKReqHandler.class);

    @Override
    public CommandType command() {
        return CommandType.COMMAND_ACK;
    }

    @Override
    public ImPacket handler(ImPacket imPacket, ImChannelContext channelContext) throws ImException, InvalidProtocolBufferException {
        return null;
    }
}
