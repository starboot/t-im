package cn.starboot.tim.server.command.handler;

import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.server.ImServerChannelContext;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通过反射获取当前类的对象实例
 *
 * Created by DELL(mxd) on 2022/9/6 12:39
 */
public class ACKReqHandler extends AbstractServerCmdHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ACKReqHandler.class);

    @Override
    public TIMCommandType command() {
        return TIMCommandType.COMMAND_ACK_REQ;
    }


	@Override
    public ImPacket handler(ImPacket imPacket, ImServerChannelContext imChannelContext) throws ImException, InvalidProtocolBufferException {
        return null;
    }
}
