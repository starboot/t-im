package cn.starboot.tim.server.intf;

import cn.starboot.tim.common.intf.TIMProcessor;
import cn.starboot.tim.common.packet.proto.*;
import cn.starboot.tim.server.ImServerChannelContext;

/**
 * 服务器处理器
 */
public interface TIMServerProcessor extends TIMProcessor {

	boolean handleBindPacket(ImServerChannelContext imChannelContext, BindPacketProto.BindPacket bindPacket);

	boolean handleLoginPacket(ImServerChannelContext imChannelContext, LoginPacketProto.LoginPacket loginPacket);

	boolean handleClosePacket(ImServerChannelContext imChannelContext, ClosePacketProto.ClosePacket closePacket);

	boolean handleAuthPacket(ImServerChannelContext imChannelContext, AuthPacketProto.AuthPacket authPacket);

	boolean handleMessagePacket(ImServerChannelContext imChannelContext, MessagePacketProto.MessagePacket messagePacket);

	boolean handleGetUserInfoPacket(ImServerChannelContext imChannelContext, UserInfoPacketProto.UserInfoPacket userInfoPacket);

	UserPacketProto.UserPacket getUserByProcessor(ImServerChannelContext imChannelContext, LoginPacketProto.LoginPacket loginPacket);
}
