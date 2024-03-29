package cn.starboot.tim.client;

import cn.starboot.tim.client.intf.Callback;
import cn.starboot.tim.common.packet.proto.ChatPacketProto;
import cn.starboot.tim.common.packet.proto.TIMEnumProto;

/**
 * Created by DELL(mxd) on 2021/12/23 20:33
 */
public class TIMClientDemo {


    public static void main(String[] args) {

		TIMClient.getInstance().start();

        // 试试功能
        doThing();
    }

    public static void doThing() {

		ChatPacketProto.ChatPacket chatPacket = ChatPacketProto.ChatPacket.newBuilder()
				.setFromId("1191998028")
				.setToId("2268509287")
				.setChatType(TIMEnumProto.TIMEnum.ChatType.PRIVATE)
				.setCreateTime(System.currentTimeMillis())
				.setContent("TIM通过aio-socket发送消息")
				.setMsgType(TIMEnumProto.TIMEnum.MsgType.TEXT)
				.setChatType(TIMEnumProto.TIMEnum.ChatType.PRIVATE)
				.build();
		TIMClient.getInstance().sendChatBody(chatPacket, null);

		TIMClient.getInstance().authReq();

		TIMClient.getInstance().login("", "", new Callback() {
			@Override
			public void success() {
				System.out.println("111");
			}

			@Override
			public void fail() {
				System.out.println("222");
			}
		});


//        TIMClient.getInstance().authReq();
//        TIMClient.getInstance().joinGroup("200");
//        ChatBody.Builder builder = ChatBody.newBuilder();
//        ChatBody body = builder.from("888")
//                .to("999")
//                .setId("120")
//                .setIsSyn(true)
//                .content("集群私聊消息")
//                .msgType(0)
//                .chatType(2)
//                .setCreateTime(new Date().getTime())
//                .groupId(null)
//                .build();
////        TIMClient.sendChatBody(body);
//        TIMClient.getInstance().ackSend(new ImPacket(Command.COMMAND_CHAT_REQ, body.toByte()), 120);
//        try {
//            Thread.sleep(8000);
//            TIMClient.getInstance().messageReq("119119");
//            TIMClient.getInstance().onlineUserId();
//            TIMClient.getInstance().logout();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }

}
