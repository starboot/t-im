package cn.starboot.tim.client.command.handler;

import cn.starboot.tim.client.ImClientChannelContext;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.packet.ImPacket;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by DELL(mxd) on 2022/1/6 22:39
 */
public class MessageRespHandler extends AbstractClientCmdHandler {

    private static final Logger log = LoggerFactory.getLogger(MessageRespHandler.class);

    @Override
    public TIMCommandType command() {
        return TIMCommandType.COMMAND_MESSAGE_RESP;
    }

    @Override
    public ImPacket handler(ImPacket imPacket, ImClientChannelContext channelContext) throws ImException, InvalidProtocolBufferException {
//        RespBody body = TIMClient.processor.instanceofHandler(packet, RespBody.class);
//        if (ObjectUtil.isEmpty(body)) {
//            log.error("消息包格式化出错");
//            return null;
//        }
//        if (body.getCode() == ImStatus.C10016.getCode() || body.getCode() == ImStatus.C10018.getCode()) {
//            if (ObjectUtil.isNotEmpty(body.getData())) {
//                JSONObject data = (JSONObject) body.getData();
//                processor(channelContext).getMessageDataAfter(data.toJSONString());
//            }
//        }
        return null;
    }
}
