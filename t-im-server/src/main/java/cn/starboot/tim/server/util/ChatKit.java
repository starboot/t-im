
package cn.starboot.tim.server.util;

import cn.hutool.core.util.ObjectUtil;
import cn.starboot.socket.core.ChannelContext;
import cn.starboot.socket.utils.json.JsonUtil;
import cn.starboot.tim.common.packet.proto.ChatPacketProto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;


/**
 * IM聊天命令工具类
 *
 */
public class ChatKit {

	private static final Logger log = LoggerFactory.getLogger(ChatKit.class);

	/**
	 * 转换为聊天消息结构;
	 * @param body
	 * @param imChannelContext
	 * @return
	 */
	public static ChatPacketProto.ChatPacket toChatBody(byte[] body, ChannelContext imChannelContext){
		ChatPacketProto.ChatPacket chatReqBody = parseChatBody(body);
		if(chatReqBody != null){
			if(chatReqBody.getFromId().length() == 0 || chatReqBody.getFromId().equals("")){
//				chatReqBody.setFrom(imChannelContext.getId());
			}
		}
		return chatReqBody;
	}

	/**
	 * 判断是否属于指定格式聊天消息;
	 * @param body .
	 * @return .
	 */
	private static ChatPacketProto.ChatPacket parseChatBody(byte[] body){
		if(body == null) {
			return null;
		}
		ChatPacketProto.ChatPacket chatReqBody = null;
		try{
			String text = new String(body, StandardCharsets.UTF_8);
		    chatReqBody = JsonUtil.toBean(text,ChatPacketProto.ChatPacket.class);
			if(chatReqBody != null){
				if(chatReqBody.getCreateTime() == 0) {
//					chatReqBody.setCreateTime(System.currentTimeMillis());
				}
				if(ObjectUtil.isNull(chatReqBody.getToId()) || chatReqBody.getToId().equals("")) {
//					chatReqBody.setId(IdUtil.randomUUID());
				}
				return chatReqBody;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return chatReqBody;
	}

	/**
	 * 判断是否属于指定格式聊天消息;
	 * @param bodyStr
	 * @return
	 */
	public static ChatPacketProto.ChatPacket parseChatBody(String bodyStr){
		if(bodyStr == null) {
			return null;
		}
		try {
//			return parseChatBody(bodyStr.getBytes(ImConst.CHARSET));
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

     /**
      * 判断用户是否在线
      * @param userId 用户ID
      * @return .
      */
     public static boolean isOnline(String userId){
//		 SetWithLock<ChannelContext> imChannelContexts = Tio.getByUserid(TCPSocketServer.getServerTioConfig(), userId);
//		 return ObjectUtil.isNotEmpty(imChannelContexts) && imChannelContexts.size() > 0;
		 return false;
	 }

	/**
	 * 获取双方会话ID
	 *
	 * @param from
	 * @param to
	 * @return
	 */
	public static String sessionId(String from, String to) {
		if (from.compareTo(to) <= 0) {
			return from + "-" + to;
		} else {
			return to + "-" + from;
		}
	}
}
