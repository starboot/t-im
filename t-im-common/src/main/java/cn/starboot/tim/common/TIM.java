package cn.starboot.tim.common;

import cn.starboot.socket.Packet;
import cn.starboot.socket.core.Aio;
import cn.starboot.socket.core.ChannelContext;
import cn.starboot.socket.enums.MaintainEnum;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.util.TIMLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 封装AIO实体类，使得集群发送和单体发送集中管理
 * Created by DELL(mxd) on 2021/12/25 23:21
 */
public class TIM {

    private static final Logger LOGGER = LoggerFactory.getLogger(TIM.class);

    private static boolean bind(MaintainEnum maintainEnum, String maintainId, ImChannelContext imChannelContext) {
		ChannelContext channelContext = imChannelContext.getChannelContext();
		boolean result = false;
		switch (maintainEnum) {
			case Bs_ID: {
				result = Aio.bindBsId(maintainId, channelContext);
				break;
			}
			case CLU_ID: {
				result = Aio.bindCluId(maintainId, channelContext);
				break;
			}
			case CLIENT_NODE_ID: {
//				Aio.bindCliNode(maintainId, channelContext);
				break;
			}
			case GROUP_ID: {
				result = Aio.bindGroup(maintainId, channelContext);
				break;
			}
			case ID: {
				result = Aio.bindId(maintainId, channelContext);
				break;
			}
			case IP: {
				result = Aio.bindIp(maintainId, channelContext);
				break;
			}
			case USER: {
				result = Aio.bindUser(maintainId, channelContext);
				break;
			}
			case TOKEN: {
				result = Aio.bindToken(maintainId, channelContext);
				break;
			}
			default: {
				TIMLogUtil.error(LOGGER, "绑定类型未知");
			}
		}
		return result;
	}

	public static boolean bindUser(String userId, ImChannelContext imChannelContext) {
		return bind(MaintainEnum.USER, userId, imChannelContext);
	}

	public static boolean bindGroup(String groupId, ImChannelContext imChannelContext) {
		return bind(MaintainEnum.GROUP_ID, groupId, imChannelContext);
	}


    public static void send(ImChannelContext channelContext, ImPacket packet) {
        Aio.send(channelContext.getChannelContext(), packet);
    }

    public static void sendToUser(String to, Packet packet) {

    }

    public static void bindGroup(ChannelContext channelContext, String group) {

    }

    public static void remove(ChannelContext channelContext, String userId) {

    }

    public static void remove(ChannelContext channelContext) {
    }


    public static void sendToGroup() {

    }

    public static void bSend() {
        // 阻塞发送
    }

    public static void ackSend(ChannelContext channelContext,Packet packet, long timeout, Integer ack) {

    }

    private TIM() {
    }
}
