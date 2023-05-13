package cn.starboot.tim.server;

import cn.starboot.socket.ChannelContextFilter;
import cn.starboot.socket.core.Aio;
import cn.starboot.socket.core.ChannelContext;
import cn.starboot.socket.enums.CloseCode;
import cn.starboot.socket.enums.MaintainEnum;
import cn.starboot.socket.utils.lock.SetWithLock;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.ImConfig;
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

	public static boolean bindUser(String userId, ImChannelContext imChannelContext) {
		return bind(MaintainEnum.USER, userId, imChannelContext);
	}

	public static boolean bindGroup(String groupId, ImChannelContext imChannelContext) {
		return bind(MaintainEnum.GROUP_ID, groupId, imChannelContext);
	}

	public static boolean bindId(String id, ImChannelContext imChannelContext) {
		return bind(MaintainEnum.ID, id, imChannelContext);
	}

	public static ChannelContext getChannelContextById(ImConfig<?> imConfig, String id) {
		return Aio.getChannelContextById(imConfig.getAioConfig(), id);
	}

	public static SetWithLock<ChannelContext> getChannelContextByUserId(ImConfig<?> imConfig, String userId) {
		return Aio.getByUser(imConfig.getAioConfig(), userId);
	}

    public static void send(ImChannelContext channelContext, ImPacket packet) {
        Aio.send(channelContext.getChannelContext(), packet);
    }

	public static void sendToId(ImConfig<?> imConfig, String toId, ImPacket imPacket) {
		singleObjectiveSend(MaintainEnum.ID, imConfig, toId, imPacket);
	}

	public static void sendToGroup(ImConfig<?> imConfig, String toId, ImPacket imPacket) {
		sendToGroup(imConfig, toId, imPacket, null);
	}
	public static void sendToGroup(ImConfig<?> imConfig, String toId, ImPacket imPacket, ChannelContextFilter channelContextFilter) {
		multiObjectiveSend(MaintainEnum.GROUP_ID, imConfig, toId, imPacket, channelContextFilter);
	}

	public static void sendToUser(ImConfig<?> imConfig, String toId, ImPacket imPacket) {
		sendToUser(imConfig, toId, imPacket, null);
	}

    public static void sendToUser(ImConfig<?> imConfig, String toId, ImPacket imPacket, ChannelContextFilter channelContextFilter) {
		multiObjectiveSend(MaintainEnum.USER, imConfig, toId, imPacket, channelContextFilter);
    }


	public static void remove(ImChannelContext imChannelContext) {
		remove(imChannelContext, null);
	}

    public static void remove(ImChannelContext imChannelContext, CloseCode closeCode) {
		close(imChannelContext, closeCode);
    }

	public static void removeGroup(ImConfig<?> imConfig, String maintainId) {
		removeGroup(imConfig, maintainId, null);
	}

	public static void removeGroup(ImConfig<?> imConfig, String maintainId, CloseCode closeCode) {
		close(MaintainEnum.GROUP_ID, imConfig, maintainId, closeCode);
	}

	public static void removeUser(ImConfig<?> imConfig, String maintainId) {
		removeUser(imConfig, maintainId, null);
	}

	public static void removeUser(ImConfig<?> imConfig, String maintainId, CloseCode closeCode) {
		close(MaintainEnum.USER, imConfig, maintainId, closeCode);
	}

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

	private static void close(ImChannelContext imChannelContext, CloseCode closeCode) {
		Aio.close(imChannelContext.getChannelContext(), closeCode);
	}

	private static void close(MaintainEnum maintainEnum, ImConfig<?> imConfig, String maintainId, CloseCode closeCode) {
		switch (maintainEnum) {
			case Bs_ID: {
				Aio.closeBsId(imConfig.getAioConfig(), maintainId, closeCode);
				break;
			}
			case CLU_ID: {
				Aio.closeClu(imConfig.getAioConfig(), maintainId, closeCode);
				break;
			}
			case CLIENT_NODE_ID: {
//				Aio.bindCliNode(maintainId, channelContext);
				break;
			}
			case GROUP_ID: {
				Aio.closeGroup(imConfig.getAioConfig(), maintainId, closeCode);
				break;
			}
			case ID: {
				Aio.closeId(imConfig.getAioConfig(), maintainId, closeCode);
				break;
			}
			case IP: {
				Aio.closeIp(imConfig.getAioConfig(), maintainId, closeCode);
				break;
			}
			case USER: {
				Aio.closeUser(imConfig.getAioConfig(), maintainId, closeCode);
				break;
			}
			case TOKEN: {
				Aio.closeToken(imConfig.getAioConfig(), maintainId, closeCode);
				break;
			}
			default: {
				TIMLogUtil.error(LOGGER, "绑定类型未知");
			}
		}
	}

	private static void multiObjectiveSend(MaintainEnum maintainEnum, ImConfig<?> imConfig, String toId, ImPacket imPacket, ChannelContextFilter channelContextFilter) {
		switch (maintainEnum) {
			case CLU_ID: {
				Aio.sendToCluId(imConfig.getAioConfig(), toId, imPacket, channelContextFilter);
				break;
			}
			case GROUP_ID: {
				Aio.sendToGroup(imConfig.getAioConfig(), toId, imPacket, channelContextFilter);
				break;
			}
			case IP: {
				Aio.sendToIp(imConfig.getAioConfig(), toId, imPacket, channelContextFilter);
				break;
			}
			case TOKEN: {
				Aio.sendToToken(imConfig.getAioConfig(), toId, imPacket, channelContextFilter);
				break;
			}
			case USER: {
				Aio.sendToUser(imConfig.getAioConfig(), toId, imPacket, channelContextFilter);
				break;
			}
			default: {
				TIMLogUtil.error(LOGGER, "");
			}
		}
	}

	private static void singleObjectiveSend(MaintainEnum maintainEnum, ImConfig<?> imConfig, String toId, ImPacket imPacket) {
		switch (maintainEnum) {
			case Bs_ID: {
				Aio.sendToBsId(imConfig.getAioConfig(), toId, imPacket);
				break;
			}
			case CLIENT_NODE_ID: {
//				Aio.sendToClientNode(imConfig.getAioConfig(), toId, imPacket);
				break;
			}
			case ID: {
				Aio.sendToId(imConfig.getAioConfig(), toId, imPacket);
				break;
			}
			default: {
				TIMLogUtil.error(LOGGER, "");
			}
		}
	}

	private static boolean unbind(MaintainEnum maintainEnum,ImConfig<?> imConfig, String maintainId, ImChannelContext imChannelContext) {
		boolean result = false;
		switch (maintainEnum) {
			case Bs_ID: {
				result = Aio.unbindBsId(imConfig.getAioConfig(), maintainId, imChannelContext.getChannelContext());
				break;
			}
			case CLU_ID: {
				result = Aio.unbindClu(maintainId, imChannelContext.getChannelContext());
				break;
			}
			case CLIENT_NODE_ID: {
//				Aio.bindCliNode(maintainId, channelContext);
				break;
			}
			case GROUP_ID: {
				result = Aio.unbindGroup(maintainId, imChannelContext.getChannelContext());
				break;
			}
			case ID: {
				result = Aio.unbindId(imConfig.getAioConfig(), maintainId, imChannelContext.getChannelContext());
				break;
			}
			case IP: {
				result = Aio.unbindIp(maintainId, imChannelContext.getChannelContext());
				break;
			}
			case USER: {
				result = Aio.unbindUser(maintainId, imChannelContext.getChannelContext());
				break;
			}
			case TOKEN: {
				result = Aio.unbindToken(maintainId, imChannelContext.getChannelContext());
				break;
			}
			default: {
				TIMLogUtil.error(LOGGER, "绑定类型未知");
			}
		}
		return result;
	}

	private static boolean unbindFromAll(MaintainEnum maintainEnum, ImChannelContext imChannelContext) {
		boolean result = false;
		switch (maintainEnum) {
			case CLU_ID: {
				result = Aio.unbindFromAllClu(imChannelContext.getChannelContext());
				break;
			}
			case GROUP_ID: {
				result = Aio.unbindFromAllGroup(imChannelContext.getChannelContext());
				break;
			}
			case IP: {
				result = Aio.unbindFromAllIp(imChannelContext.getChannelContext());
				break;
			}
			case USER: {
				result = Aio.unbindFromAllUser(imChannelContext.getChannelContext());
				break;
			}
			case TOKEN: {
				result = Aio.unbindFromAllToken(imChannelContext.getChannelContext());
				break;
			}
			default: {
				TIMLogUtil.error(LOGGER, "绑定类型未知");
			}
		}
		return result;
	}

	private TIM() {
	}
}
