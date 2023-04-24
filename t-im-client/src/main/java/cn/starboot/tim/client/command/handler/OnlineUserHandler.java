package cn.starboot.tim.client.command.handler;

import cn.starboot.tim.client.command.AbstractClientCmdHandler;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.packet.ReqCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by DELL(mxd) on 2022/1/6 22:40
 */
public class OnlineUserHandler extends AbstractClientCmdHandler {

    private static final Logger log = LoggerFactory.getLogger(OnlineUserHandler.class);

    @Override
    public ReqCommandType command() {
        return ReqCommandType.COMMAND_RESP_REQ;
    }

    @Override
    public ImPacket handler(ImPacket imPacket, ImChannelContext channelContext) throws ImException, InvalidProtocolBufferException {
//        RespBody respBody = TIMClient.processor.instanceofHandler(packet, RespBody.class);
//        if (ObjectUtil.isEmpty(respBody)) {
//            log.error("消息包格式化出错");
//            return null;
//        }
//        if (respBody.getCode() == ImStatus.C10005.getCode()) {
//            JSONArray convert = Convert.convert(JSONArray.class, respBody.getData());
//            processor(channelContext).getOnlineUserIdAfter(convert.toJSONString());
//        }
        return null;
    }
}
