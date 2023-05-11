package cn.starboot.tim.client.command.handler;


import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.command.ReqClientCommandType;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.command.ReqServerCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * Created by DELL(mxd) on 2022/1/6 17:14
 */
public class LoginRespHandler extends AbstractClientCmdHandler {

    @Override
    public ReqClientCommandType command() {
        return ReqClientCommandType.COMMAND_LOGIN_RESP;
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
