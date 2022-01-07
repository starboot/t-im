package org.tim.client.command.handler;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.client.TIMClient;
import org.tim.client.command.AbstractCmdHandler;
import org.tim.common.ImStatus;
import org.tim.common.exception.ImException;
import org.tim.common.packets.Command;
import org.tim.common.packets.RespBody;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;


/**
 * Created by DELL(mxd) on 2022/1/6 22:40
 */
public class OnlineUserHandler extends AbstractCmdHandler {

    private static final Logger log = LoggerFactory.getLogger(OnlineUserHandler.class);

    @Override
    public Command command() {
        return Command.COMMAND_GET_USER_RESP;
    }

    @Override
    public Packet handler(Packet packet, ChannelContext channelContext) throws ImException {
        RespBody respBody = TIMClient.processor.instanceofHandler(packet, RespBody.class);
        if (ObjectUtil.isEmpty(respBody)) {
            log.error("消息包格式化出错");
            return null;
        }
        if (respBody.getCode() == ImStatus.C10005.getCode()) {
            JSONArray convert = Convert.convert(JSONArray.class, respBody.getData());
            processor(channelContext).getOnlineUserIdAfter(convert.toJSONString());
        }
        return null;
    }
}
