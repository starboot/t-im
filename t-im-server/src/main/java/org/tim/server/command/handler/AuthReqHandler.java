package org.tim.server.command.handler;

import cn.hutool.core.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.common.ImConst;
import org.tim.common.ImPacket;
import org.tim.common.ImStatus;
import org.tim.common.packets.AuthReqBody;
import org.tim.common.packets.Command;
import org.tim.common.packets.RespBody;
import org.tim.server.command.AbstractCmdHandler;
import org.tim.server.protocol.IMServer;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.core.intf.Packet;

import java.util.Objects;

/**
 * Created by DELL(mxd) on 2021/12/25 11:57
 */
public class AuthReqHandler extends AbstractCmdHandler {

    private static final Logger log = LoggerFactory.getLogger(AuthReqHandler.class);

    @Override
    public ImPacket handler(Packet packet, ChannelContext channelContext) {
        AuthReqBody authReqBody = IMServer.handlerProcessor.instanceofHandler(packet, AuthReqBody.class);
        if (ObjectUtil.isEmpty(authReqBody)) {
            log.error("消息包格式化出错");
            return null;
        }
        if (Objects.nonNull(authReqBody) && authReqBody.getToken() != null && authReqBody.getToken().length() > 0) {
            String token = authReqBody.getToken() == null ? "" : authReqBody.getToken();
            String data = token +  ImConst.AUTH_KEY;
            authReqBody.setToken(data);
            authReqBody.setCmd(Command.COMMAND_AUTH_RESP.getNumber());
            RespBody respBody = new RespBody(Command.COMMAND_AUTH_RESP,ImStatus.C10009).setData(authReqBody);
            Tio.send(channelContext, new ImPacket(Command.COMMAND_AUTH_RESP, respBody.toByte()));
        }else {
            RespBody respBody = new RespBody(Command.COMMAND_AUTH_RESP, ImStatus.C10010);
            Tio.send(channelContext, new ImPacket(Command.COMMAND_AUTH_RESP, respBody.toByte()));
        }
        return null;
    }

    @Override
    public Command command() {
        return Command.COMMAND_AUTH_REQ;
    }
}
