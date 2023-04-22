package cn.starboot.tim.client.command.handler;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.tim.client.TIMClient;
import org.tim.client.command.AbstractCmdHandler;
import org.tim.client.intf.Callback;
import org.tim.common.ImPacket;
import org.tim.common.ImStatus;
import org.tim.common.exception.ImException;
import org.tim.common.packets.Command;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;

import java.nio.charset.StandardCharsets;

/**
 * Created by DELL(mxd) on 2022/1/6 17:14
 */
public class LoginHandler extends AbstractCmdHandler {

    @Override
    public Command command() {
        return Command.COMMAND_LOGIN_RESP;
    }

    @Override
    public Packet handler(Packet packet, ChannelContext channelContext) throws ImException {
        if (packet instanceof ImPacket) {
            ImPacket imPacket = (ImPacket) packet;
            byte[] body = imPacket.getBody();
            String s = new String(body, StandardCharsets.UTF_8);
            JSONObject jsonObject = JSONUtil.parseObj(s);
            Callback loginCallback = (Callback) channelContext.get("loginCallback");
            if (imPacket.getCommand() == Command.COMMAND_LOGIN_RESP && jsonObject.getInt("code") == ImStatus.C10007.getCode()) {
                loginCallback.success();
                processor(channelContext).afterLogin();
            }else {
                loginCallback.fail();
            }
            Integer id = jsonObject.getInt("id");
            boolean isSyn = jsonObject.getBool("syn");
            if (id > 0 && !isSyn) {
                synHandler(channelContext, packet, id);
            }
        }
        return null;
    }
}
