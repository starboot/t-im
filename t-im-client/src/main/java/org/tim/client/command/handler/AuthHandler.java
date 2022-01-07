package org.tim.client.command.handler;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.client.TIMClient;
import org.tim.client.command.AbstractCmdHandler;
import org.tim.common.ImStatus;
import org.tim.common.exception.ImException;
import org.tim.common.packets.AuthReqBody;
import org.tim.common.packets.Command;
import org.tim.common.packets.RespBody;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;

/**
 * Created by DELL(mxd) on 2022/1/6 22:38
 */
public class AuthHandler extends AbstractCmdHandler {

    private static final Logger log = LoggerFactory.getLogger(AuthHandler.class);
    @Override
    public Command command() {
        return Command.COMMAND_AUTH_RESP;
    }

    @Override
    public Packet handler(Packet packet, ChannelContext channelContext) throws ImException {
        RespBody body = TIMClient.processor.instanceofHandler(packet, RespBody.class);
        if (ObjectUtil.isEmpty(body)) {
            log.error("消息包格式化出错");
            return null;
        }
        if (body.getCode() == ImStatus.C10009.getCode()) {
            processor(channelContext).authSuccess(Convert.convert(AuthReqBody.class, body.getData()).getToken());
        }
        return null;
    }
}
