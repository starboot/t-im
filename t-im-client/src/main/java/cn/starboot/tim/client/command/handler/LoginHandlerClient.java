package cn.starboot.tim.client.command.handler;


import cn.starboot.tim.client.command.AbstractClientCmdHandler;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.packet.CommandType;
import cn.starboot.tim.common.packet.ImPacket;
import com.google.protobuf.InvalidProtocolBufferException;

import java.nio.charset.StandardCharsets;

/**
 * Created by DELL(mxd) on 2022/1/6 17:14
 */
public class LoginHandlerClient extends AbstractClientCmdHandler {

    @Override
    public CommandType command() {
        return CommandType.COMMAND_LOGIN;
    }

    @Override
    public ImPacket handler(ImPacket imPacket, ImChannelContext channelContext) throws ImException, InvalidProtocolBufferException {
//        if (packet instanceof ImPacket) {
//            ImPacket imPacket = (ImPacket) packet;
//            byte[] body = imPacket.getBody();
//            String s = new String(body, StandardCharsets.UTF_8);
//            JSONObject jsonObject = JSONUtil.parseObj(s);
//            Callback loginCallback = (Callback) channelContext.get("loginCallback");
//            if (imPacket.getCommand() == Command.COMMAND_LOGIN_RESP && jsonObject.getInt("code") == ImStatus.C10007.getCode()) {
//                loginCallback.success();
//                processor(channelContext).afterLogin();
//            }else {
//                loginCallback.fail();
//            }
//            Integer id = jsonObject.getInt("id");
//            boolean isSyn = jsonObject.getBool("syn");
//            if (id > 0 && !isSyn) {
//                synHandler(channelContext, packet, id);
//            }
//        }
        return null;
    }
}
