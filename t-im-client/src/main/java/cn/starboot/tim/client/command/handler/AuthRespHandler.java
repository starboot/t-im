package cn.starboot.tim.client.command.handler;

import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.packet.ImPacket;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by DELL(mxd) on 2022/1/6 22:38
 */
public class AuthRespHandler extends AbstractClientCmdHandler {

    private static final Logger log = LoggerFactory.getLogger(AuthRespHandler.class);

    @Override
    public TIMCommandType command() {
        return TIMCommandType.COMMAND_AUTH_RESP;
    }

    @Override
    public ImPacket handler(ImPacket imPacket, ImChannelContext channelContext) throws ImException, InvalidProtocolBufferException {
//        RespBody body = TIMClient.processor.instanceofHandler(packet, RespBody.class);
//
//        if (Objects.isEmpty(body)) {
//            log.error("消息包格式化出错");
//            return null;
//        }
//        if (body.getCode() == ImStatus.C10009.getCode()) {
//            AuthReqBody data = null;
//            try {
//                data = Convert.convert(AuthReqBody.class, body.getData());
//            } catch (Exception | Error e) {
//                if (body.getData() instanceof AuthReqBody) {
//                    data = (AuthReqBody) body.getData();
//                }else if (body.getData() instanceof  JSONObject) {
//                    data = ((JSONObject) body.getData()).toJavaObject(AuthReqBody.class);
//                }
//            }
//            if (data == null) {
//                log.error("AuthHandler : Class convert failed");
//                return null;
//            }
//            processor(channelContext).authSuccess(data.getToken());
//        }
        return null;
    }


}
