package org.tim.server.command.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.common.ImStatus;
import org.tim.common.packets.CloseReqBody;
import org.tim.common.packets.Command;
import org.tim.server.command.AbstractCmdHandler;
import org.tim.server.helper.TIM;
import org.tim.server.protocol.IMServer;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;

/**
 * Created by DELL(mxd) on 2021/12/25 14:48
 */
public class CloseReqHandler extends AbstractCmdHandler {

    private static final Logger log = LoggerFactory.getLogger(CloseReqHandler.class);

    @Override
    public Command command() {
        return Command.COMMAND_CLOSE_REQ;
    }

    @Override
    public Packet handler(Packet packet, ChannelContext channelContext) {
        CloseReqBody closeReqBody = null;
        try{
            closeReqBody = IMServer.handlerProcessor.instanceofHandler(packet, CloseReqBody.class);
        }catch (Exception e) {
            //关闭请求消息格式不正确
            log.error(ImStatus.C10020.getText());
        }
        if(closeReqBody == null || closeReqBody.getUserId() == null){
            log.error("用户发送异常关闭请求，将其强行断开");
            TIM.remove(channelContext);
        }else{
            TIM.remove(channelContext, closeReqBody.getUserId());
        }
        return null;
    }
}
