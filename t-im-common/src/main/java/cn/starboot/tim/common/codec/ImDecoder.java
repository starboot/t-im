package cn.starboot.tim.common.codec;

import cn.starboot.socket.core.ChannelContext;
import cn.starboot.socket.core.WriteBuffer;
import cn.starboot.tim.common.exception.ImDecodeException;
import cn.starboot.tim.common.packet.ImPacket;

public interface ImDecoder {

	ImPacket decode(WriteBuffer writeBuffer, ChannelContext channelContext) throws ImDecodeException;
}
