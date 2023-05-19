package cn.starboot.tim.server;

import cn.starboot.socket.ChannelContextFilter;
import cn.starboot.socket.core.Aio;
import cn.starboot.socket.core.AioConfig;
import cn.starboot.socket.core.ChannelContext;
import cn.starboot.socket.enums.CloseCode;
import cn.starboot.socket.enums.MaintainEnum;
import cn.starboot.socket.utils.lock.SetWithLock;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.util.TIMLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 封装AIO实体类，使得集群发送和单体发送集中管理
 * Created by DELL(mxd) on 2021/12/25 23:21
 */
public class TIMServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TIMServer.class);

	public static boolean bindBsId(String bsId, ImChannelContext<ImServerConfig> imChannelContext) {
		return bind(MaintainEnum.Bs_ID, bsId, imChannelContext);
	}

	public static boolean bindId(String id, ImChannelContext<ImServerConfig> imChannelContext) {
		return bind(MaintainEnum.ID, id, imChannelContext);
	}

	public static boolean bindIP(String ip, ImChannelContext<ImServerConfig> imChannelContext) {
		return bind(MaintainEnum.IP, ip, imChannelContext);
	}

	public static boolean bindGroup(String groupId, ImChannelContext<ImServerConfig> imChannelContext) {
		return bind(MaintainEnum.GROUP_ID, groupId, imChannelContext);
	}

	public static boolean bindToken(String tokenId, ImChannelContext<ImServerConfig> imChannelContext) {
		return bind(MaintainEnum.TOKEN, tokenId, imChannelContext);
	}

	public static boolean bindUser(String userId, ImChannelContext<ImServerConfig> imChannelContext) {
		return bind(MaintainEnum.USER, userId, imChannelContext);
	}

	public static ChannelContext getChannelContextById(ImServerConfig imConfig, String id) {
		return Aio.getChannelContextById(imConfig.getAioConfig(), id);
	}

	public static SetWithLock<ChannelContext> getChannelContextByUserId(ImServerConfig imConfig, String userId) {
		return Aio.getByUser(imConfig.getAioConfig(), userId);
	}

    public static void send(ImChannelContext<ImServerConfig> channelContext, ImPacket packet) {
        Aio.send(channelContext.getChannelContext(), packet);
    }

	public static void sendToBsId(ImServerConfig imConfig, String toBsId, ImPacket imPacket) {
		singleObjectiveSend(MaintainEnum.Bs_ID, imConfig.getAioConfig(), toBsId, imPacket);
	}

	public static void sendToId(ImServerConfig imConfig, String toId, ImPacket imPacket) {
		singleObjectiveSend(MaintainEnum.ID, imConfig.getAioConfig(), toId, imPacket);
	}

	public static void sendToIP(ImServerConfig imConfig, String toIP, ImPacket imPacket) {
		sendToIP(imConfig, toIP, imPacket, null);
	}

	public static void sendToIP(ImServerConfig imConfig, String toIP, ImPacket imPacket, ChannelContextFilter channelContextFilter) {
		multiObjectiveSend(MaintainEnum.IP, imConfig.getAioConfig(), toIP, imPacket, channelContextFilter);
	}

	public static void sendToGroup(ImServerConfig imConfig, String toId, ImPacket imPacket) {
		sendToGroup(imConfig, toId, imPacket, null);
	}

	public static void sendToGroup(ImServerConfig imConfig, String toId, ImPacket imPacket, ChannelContextFilter channelContextFilter) {
		multiObjectiveSend(MaintainEnum.GROUP_ID, imConfig.getAioConfig(), toId, imPacket, channelContextFilter);
	}

	public static void sendToToken(ImServerConfig imConfig, String toTokenId, ImPacket imPacket) {
		sendToToken(imConfig, toTokenId, imPacket, null);
	}

	public static void sendToToken(ImServerConfig imConfig, String toTokenId, ImPacket imPacket, ChannelContextFilter channelContextFilter) {
		multiObjectiveSend(MaintainEnum.TOKEN, imConfig.getAioConfig(), toTokenId, imPacket, channelContextFilter);
	}

	public static void sendToUser(ImServerConfig imConfig, String toId, ImPacket imPacket) {
		sendToUser(imConfig, toId, imPacket, null);
	}

    public static void sendToUser(ImServerConfig imConfig, String toId, ImPacket imPacket, ChannelContextFilter channelContextFilter) {
		multiObjectiveSend(MaintainEnum.USER, imConfig.getAioConfig(), toId, imPacket, channelContextFilter);
    }

	public static void remove(ImChannelContext<ImServerConfig> imChannelContext) {
		remove(imChannelContext, null);
	}

    public static void remove(ImChannelContext<ImServerConfig> imChannelContext, CloseCode closeCode) {
		close(imChannelContext, closeCode);
    }

	public static void removeGroup(ImServerConfig imConfig, String maintainId) {
		removeGroup(imConfig, maintainId, null);
	}

	public static void removeGroup(ImServerConfig imConfig, String maintainId, CloseCode closeCode) {
		close(MaintainEnum.GROUP_ID, imConfig, maintainId, closeCode);
	}

	public static void removeUser(ImServerConfig imConfig, String maintainId) {
		removeUser(imConfig, maintainId, null);
	}

	public static void removeUser(ImServerConfig imConfig, String maintainId, CloseCode closeCode) {
		close(MaintainEnum.USER, imConfig, maintainId, closeCode);
	}

	public static boolean unbindBsId(String bsId, ImChannelContext<ImServerConfig> imChannelContext) {
		return unbind(MaintainEnum.Bs_ID, bsId, imChannelContext);
	}

	public static boolean unbindId(String id, ImChannelContext<ImServerConfig> imChannelContext) {
		return unbind(MaintainEnum.ID, id, imChannelContext);
	}

	public static boolean unbindIP(String ip, ImChannelContext<ImServerConfig> imChannelContext) {
		return unbind(MaintainEnum.IP, ip, imChannelContext);
	}

	public static boolean unbindGroup(String groupId, ImChannelContext<ImServerConfig> imChannelContext) {
		return unbind(MaintainEnum.GROUP_ID, groupId, imChannelContext);
	}

	public static boolean unbindToken(String tokenId, ImChannelContext<ImServerConfig> imChannelContext) {
		return unbind(MaintainEnum.TOKEN, tokenId, imChannelContext);
	}

	public static boolean unbindUser(String userId, ImChannelContext<ImServerConfig> imChannelContext) {
		return unbind(MaintainEnum.USER, userId, imChannelContext);
	}

	private static boolean bind(MaintainEnum maintainEnum, String maintainId, ImChannelContext<ImServerConfig> imChannelContext) {
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
				TIMLogUtil.error(LOGGER, "TIMServer: bind Type not exist");
			}
		}
		return result;
	}

	private static void close(ImChannelContext<ImServerConfig> imChannelContext, CloseCode closeCode) {
		Aio.close(imChannelContext.getChannelContext(), closeCode);
	}

	private static void close(MaintainEnum maintainEnum, ImServerConfig imConfig, String maintainId, CloseCode closeCode) {
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

	private static void multiObjectiveSend(MaintainEnum maintainEnum, AioConfig aioConfig, String toId, ImPacket imPacket, ChannelContextFilter channelContextFilter) {
		switch (maintainEnum) {
			case CLU_ID: {
				Aio.sendToCluId(aioConfig, toId, imPacket, channelContextFilter);
				break;
			}
			case GROUP_ID: {
				Aio.sendToGroup(aioConfig, toId, imPacket, channelContextFilter);
				break;
			}
			case IP: {
				Aio.sendToIp(aioConfig, toId, imPacket, channelContextFilter);
				break;
			}
			case TOKEN: {
				Aio.sendToToken(aioConfig, toId, imPacket, channelContextFilter);
				break;
			}
			case USER: {
				Aio.sendToUser(aioConfig, toId, imPacket, channelContextFilter);
				break;
			}
			default: {
				TIMLogUtil.error(LOGGER, "");
			}
		}
	}

	private static void singleObjectiveSend(MaintainEnum maintainEnum, AioConfig aioConfig, String toId, ImPacket imPacket) {
		switch (maintainEnum) {
			case Bs_ID: {
				Aio.sendToBsId(aioConfig, toId, imPacket);
				break;
			}
			case CLIENT_NODE_ID: {
//				Aio.sendToClientNode(imConfig.getAioConfig(), toId, imPacket);
				break;
			}
			case ID: {
				Aio.sendToId(aioConfig, toId, imPacket);
				break;
			}
			default: {
				TIMLogUtil.error(LOGGER, "");
			}
		}
	}

	private static boolean unbind(MaintainEnum maintainEnum, String maintainId, ImChannelContext<ImServerConfig> imChannelContext) {
		boolean result = false;
		switch (maintainEnum) {
			case Bs_ID: {
				result = Aio.unbindBsId(imChannelContext.getConfig().getAioConfig(), maintainId, imChannelContext.getChannelContext());
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
				result = Aio.unbindId(imChannelContext.getConfig().getAioConfig(), maintainId, imChannelContext.getChannelContext());
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
				TIMLogUtil.error(LOGGER, "TIMServer: unbind Type not exist");
			}
		}
		return result;
	}

	private static boolean unbindFromAll(MaintainEnum maintainEnum, ImChannelContext<ImServerConfig> imChannelContext) {
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
				TIMLogUtil.error(LOGGER, "TIMServer: unbind Type not exist");
			}
		}
		return result;
	}

	private TIMServer() {
	}
}
