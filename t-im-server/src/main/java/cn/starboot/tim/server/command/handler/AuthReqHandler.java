package cn.starboot.tim.server.command.handler;

import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.server.ImServerChannelContext;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 授权方式暂未限制
 * Created by DELL(mxd) on 2021/12/25 11:57
 */
public class AuthReqHandler extends AbstractServerCmdHandler {

    private static final Logger log = LoggerFactory.getLogger(AuthReqHandler.class);

    @Override
    public TIMCommandType command() {
        return TIMCommandType.COMMAND_AUTH_REQ;
    }

	@Override
    public ImPacket handler(ImPacket imPacket, ImServerChannelContext channelContext) {

//        AuthReqBody authReqBody = new AuthReqBody();
//        if (ObjectUtil.isEmpty(authReqBody)) {
//            log.error("消息包格式化出错");
//            return null;
//        }
//        if (ObjectUtil.isNotEmpty(authReqBody) && authReqBody.getToken() != null && authReqBody.getToken().length() > 0) {
//            String token = authReqBody.getToken() == null ? "" : authReqBody.getToken();
//            String data = token +  ImConst.AUTH_KEY;
//            authReqBody.setToken(data);
//            authReqBody.setCmd(Command.COMMAND_AUTH_RESP.getNumber());
//            RespBody respBody = new RespBody(Command.COMMAND_AUTH_RESP,ImStatus.C10009).setData(authReqBody);
//            TIM.send(channelContext, new ImPacket(Command.COMMAND_AUTH_RESP, respBody.toByte()));
//        }else {
//            RespBody respBody = new RespBody(Command.COMMAND_AUTH_RESP, ImStatus.C10010);
//            TIM.send(channelContext, new ImPacket(Command.COMMAND_AUTH_RESP, respBody.toByte()));
//        }

//        return ImPacket
//				.newBuilder()
//				.setTIMCommandType(TIMCommandType.COMMAND_REQ_RESP)
//				.setData(RespPacketProto
//						.RespPacket
//						.newBuilder()
//						.setIsSyn(false)
//						.setCode(1)
//						.setMsg("")
//						.build()
//						.toByteArray())
//				.build();
        return null;
    }

}
