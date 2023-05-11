package cn.starboot.tim.server.command.handler;

import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.command.ReqServerCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.RespPacketProto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 授权方式暂未限制
 * Created by DELL(mxd) on 2021/12/25 11:57
 */
public class AuthReqHandler extends AbstractServerCmdHandler {

    private static final Logger log = LoggerFactory.getLogger(AuthReqHandler.class);

    @Override
    public ReqServerCommandType command() {
        return ReqServerCommandType.COMMAND_AUTH_REQ;
    }

    @Override
    public ImPacket handler(ImPacket imPacket, ImChannelContext channelContext) {

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
        return new ImPacket(ReqServerCommandType.COMMAND_REQ_RESP,
				RespPacketProto
						.RespPacket
						.newBuilder()
						.setIsSyn(false)
						.setCode(1)
						.setMsg("")
						.build()
						.toByteArray());
    }

}
