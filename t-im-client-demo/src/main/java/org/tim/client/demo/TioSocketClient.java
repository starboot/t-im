package org.tim.client.demo;

import org.tim.client.Options;
import org.tim.client.TIMClient;
import org.tim.common.packets.ChatBody;
import org.tio.core.Node;

import java.util.Date;

/**
 * Created by DELL(mxd) on 2021/12/23 20:33
 */
public class TioSocketClient {

    static TIMClient client;

    public static void main(String[] args) throws Exception {

        // 初始化并登录
        Options options = new Options(new Node("127.0.0.1", 8888));
        TIMClient.start(options);
        TIMClient.login("888", "mi191919",(isSuccess, timClient) -> {
            if (isSuccess) {
                // 登陆成功
                client = timClient;
                System.out.println("登陆成功");
            }else {
                System.out.println("登陆失败");
            }
        });

        // 试试功能
        doThing();
    }

    public static void doThing() {
        client.authReq();
        client.joinGroup("200");
        ChatBody.Builder builder = ChatBody.newBuilder();
        ChatBody body = builder.from("888")
                .to("119119")
                .content("集群私聊消息")
                .msgType(0)
                .chatType(2)
                .setCreateTime(new Date().getTime())
                .groupId(null)
                .build();
        client.sendChatBody(body);
        try {
            Thread.sleep(2000);
            client.messageReq("119119");
            client.onlineUserId();
            client.logout();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
