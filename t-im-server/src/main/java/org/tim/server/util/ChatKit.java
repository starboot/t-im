
package org.tim.server.util;


import cn.hutool.core.util.IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.common.ImConst;
import org.tim.common.packets.ChatBody;
import org.tim.server.protocol.tcp.TCPSocketServer;
import org.tim.server.util.json.JsonKit;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.utils.lock.SetWithLock;

import java.nio.charset.StandardCharsets;
import java.util.Objects;


/**
 * IM聊天命令工具类
 * @date 2018-09-05 23:29:30
 * @author WChao
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
	public static ChatBody toChatBody(byte[] body, ChannelContext imChannelContext){
		ChatBody chatReqBody = parseChatBody(body);
		if(chatReqBody != null){
			if(chatReqBody.getFrom().length() == 0 || chatReqBody.getFrom().equals("")){
				chatReqBody.setFrom(imChannelContext.getId());
			}
		}
		return chatReqBody;
	}

	/**
	 * 判断是否属于指定格式聊天消息;
	 * @param body
	 * @return
	 */
	private static ChatBody parseChatBody(byte[] body){
		if(body == null) {
			return null;
		}
		ChatBody chatReqBody = null;
		try{
			String text = new String(body, StandardCharsets.UTF_8);
		    chatReqBody = JsonKit.toBean(text,ChatBody.class);
			if(chatReqBody != null){
				if(chatReqBody.getCreateTime() == null) {
					chatReqBody.setCreateTime(System.currentTimeMillis());
				}
				if(Objects.isNull(chatReqBody.getId()) || chatReqBody.getId().equals("")) {
					chatReqBody.setId(IdUtil.randomUUID());
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
	public static ChatBody parseChatBody(String bodyStr){
		if(bodyStr == null) {
			return null;
		}
		try {
			return parseChatBody(bodyStr.getBytes(ImConst.CHARSET));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

     /**
      * 判断用户是否在线
      * @param userId 用户ID
      * @return
      */
     public static boolean isOnline(String userId){
		 SetWithLock<ChannelContext> imChannelContexts = Tio.getByUserid(TCPSocketServer.getServerTioConfig(), userId);
		 return Objects.isNull(imChannelContexts) || imChannelContexts.size() == 0;
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
