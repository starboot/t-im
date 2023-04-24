package cn.starboot.tim.client.intf;

import cn.starboot.tim.common.intf.Processor;
import cn.starboot.tim.common.packet.proto.ChatPacketProto;

/**
 * Created by DELL(mxd) on 2022/1/6 13:55
 */
public interface ClientProcessor extends Processor {

    void afterLogin();

    void OnMessage(ChatPacketProto.ChatPacket body);

    void authSuccess(String key);

    void getMessageDataAfter(String data);

    void getOnlineUserIdAfter(String ids);

    void ackTimeOut(Integer id);

    void connectException(String s);
}
