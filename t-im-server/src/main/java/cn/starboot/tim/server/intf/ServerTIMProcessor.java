package cn.starboot.tim.server.intf;

import cn.starboot.tim.common.intf.TIMProcessor;
import cn.starboot.tim.common.packet.proto.BindPacketProto;

/**
 * 服务器处理器
 */
public interface ServerTIMProcessor extends TIMProcessor {

	boolean handleBindPacket(BindPacketProto.BindPacket bindPacket);
}
