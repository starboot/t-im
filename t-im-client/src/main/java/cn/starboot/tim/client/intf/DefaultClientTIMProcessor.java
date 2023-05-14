package cn.starboot.tim.client.intf;

import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.ChatPacketProto;

/**
 * Created by DELL(mxd) on 2022/1/6 20:32
 */
public class DefaultClientTIMProcessor implements ClientTIMProcessor {

    @Override
    public void afterLogin() {
        System.out.println("登陆后 do thing...");
    }

    @Override
    public void OnMessage(ChatPacketProto.ChatPacket body) {

//        System.out.println("收到消息: " + body.toString());
        System.out.println("收到消息: " + body.getContent());
    }

    @Override
    public void authSuccess(String key) {
        System.out.println(key);
    }

    @Override
    public void getMessageDataAfter(String data) {

        System.out.println(data);
    }

    @Override
    public void getOnlineUserIdAfter(String ids) {

        System.out.println(ids);
    }

    @Override
    public void ackTimeOut(Integer id) {
    }

    @Override
    public void connectException(String s) {
        System.out.println("连接错误: " + s);
    }

	@Override
	public boolean beforeSend(ImChannelContext imChannelContext, ImPacket packet) {
		return true;
	}

	@Override
	public boolean handleChatPacket(ImChannelContext imChannelContext, ChatPacketProto.ChatPacket chatPacket) {
		return true;
	}
}
