package org.tim.server.command.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.common.ImPacket;
import org.tim.common.ImStatus;
import org.tim.common.packets.*;
import org.tim.server.command.AbstractCmdHandler;
import org.tim.server.helper.TIM;
import org.tim.server.protocol.IMServer;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.core.intf.Packet;


/**
 * Created by DELL(mxd) on 2021/12/25 15:29
 */
public class JoinGroupReqHandler extends AbstractCmdHandler {

    private static final Logger log = LoggerFactory.getLogger(JoinGroupReqHandler.class);

    @Override
    public Command command() {
        return Command.COMMAND_JOIN_GROUP_REQ;
    }

    @Override
    public Packet handler(Packet packet, ChannelContext channelContext) {
        Group joinGroup = IMServer.handlerProcessor.instanceofHandler(packet, Group.class);
        if (ObjectUtil.isEmpty(joinGroup)) {
            log.error("消息包格式化出错");
            return null;
        }
        //绑定群组;
        String groupId = joinGroup.getGroupId();
        if (StrUtil.isBlank(groupId)) {
            log.error("group is null,{}", channelContext);
            RespBody respBody = new JoinGroupRespBody(Command.COMMAND_JOIN_GROUP_RESP, ImStatus.C10012)
                    .setResult(JoinGroupResult.JOIN_GROUP_RESULT_UNKNOWN)
                    .setGroup(groupId);
            TIM.send(channelContext, new ImPacket(Command.COMMAND_JOIN_GROUP_RESP, respBody.toByte()));
            return null;
        }
        // 如果加入群聊前有群聊处理器 则在此之星
        //处理完处理器内容后
        Tio.bindGroup(channelContext, groupId);
        return null;
    }
}
