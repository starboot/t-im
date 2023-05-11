package cn.starboot.tim.common.packet;

import cn.starboot.tim.common.command.TIMCommandType;

public interface ImPacketFactory {

	ImPacket createImPacket(TIMCommandType timCommandType, byte[] data);
}
