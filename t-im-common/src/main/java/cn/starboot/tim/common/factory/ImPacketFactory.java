package cn.starboot.tim.common.factory;

import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.packet.ImPacket;

public interface ImPacketFactory {

	ImPacket createImPacket(TIMCommandType timCommandType, byte[] data);
}
