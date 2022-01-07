package org.tim.client.intf;

import org.tim.common.packets.ChatBody;
import org.tim.common.packets.UserMessageData;

import java.util.Set;

/**
 * Created by DELL(mxd) on 2022/1/6 20:32
 */
public class DefaultMessageProcessor implements MessageProcessor{

    @Override
    public void afterLogin() {
        System.out.println("登陆后 do thing...");
    }

    @Override
    public void OnMessage(ChatBody body) {

        System.out.println("收到消息: " + body.toJsonString());
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
}
