package cn.starboot.tim.common.plugin;

import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.packet.ImPacket;

import java.util.Objects;

public class TIMPlugin {

	public boolean beforeProcess(ImPacket imPacket, ImChannelContext imChannelContext) {
		byte[] data = imPacket.getData();
		return !(Objects.isNull(data) || data.length == 0);
	}
}
