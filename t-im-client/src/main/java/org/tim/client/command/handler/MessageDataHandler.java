package org.tim.client.command.handler;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.client.TIMClient;
import org.tim.client.command.AbstractCmdHandler;
import org.tim.common.ImStatus;
import org.tim.common.exception.ImException;
import org.tim.common.packets.Command;
import org.tim.common.packets.RespBody;
import org.tim.common.packets.UserMessageData;
import org.tim.common.util.json.JsonKit;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;

/**
 * Created by DELL(mxd) on 2022/1/6 22:39
 */
public class MessageDataHandler extends AbstractCmdHandler {

    private static final Logger log = LoggerFactory.getLogger(MessageDataHandler.class);

    @Override
    public Command command() {
        return Command.COMMAND_GET_MESSAGE_RESP;
    }

    @Override
    public Packet handler(Packet packet, ChannelContext channelContext) throws ImException {
        RespBody body = TIMClient.processor.instanceofHandler(packet, RespBody.class);
        if (ObjectUtil.isEmpty(body)) {
            log.error("消息包格式化出错");
            return null;
        }
        if (body.getCode() == ImStatus.C10016.getCode() || body.getCode() == ImStatus.C10018.getCode()) {
            if (ObjectUtil.isNotEmpty(body.getData())) {
                JSONObject data = (JSONObject) body.getData();
                processor(channelContext).getMessageDataAfter(data.toJSONString());
            }
        }
        return null;
    }
}
