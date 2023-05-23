package cn.starboot.tim.common.factory;

import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.packet.proto.RespPacketProto;

public interface ImPacketFactory {

	ImPacket createImPacket(TIMCommandType timCommandType, byte[] data);
}
