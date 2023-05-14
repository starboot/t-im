package cn.starboot.tim.server.intf;

import cn.starboot.tim.common.entity.User;
import cn.starboot.tim.common.intf.TIMProcessor;
import cn.starboot.tim.common.packet.proto.BindPacketProto;
import cn.starboot.tim.common.packet.proto.ClosePacketProto;
import cn.starboot.tim.common.packet.proto.LoginPacketProto;
import cn.starboot.tim.server.ImServerChannelContext;

/**
 * 服务器处理器
 */
public interface ServerTIMProcessor extends TIMProcessor {

	boolean handleBindPacket(ImServerChannelContext imChannelContext, BindPacketProto.BindPacket bindPacket);

	boolean handleLoginPacket(ImServerChannelContext imChannelContext, LoginPacketProto.LoginPacket loginPacket);

	boolean handleClosePacket(ImServerChannelContext imChannelContext, ClosePacketProto.ClosePacket closePacket);

	boolean handleAuthPacket(ImServerChannelContext imChannelContext, ClosePacketProto.ClosePacket closePacket);

	User getUserByProcessor(ImServerChannelContext imChannelContext, LoginPacketProto.LoginPacket loginPacket);
}
