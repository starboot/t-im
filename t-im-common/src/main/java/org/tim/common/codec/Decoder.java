
package org.tim.common.codec;

import org.tio.core.ChannelContext;
import org.tio.core.exception.TioDecodeException;
import org.tio.core.intf.Packet;

import java.nio.ByteBuffer;

/**
 * 版本: [1.0]
 * 功能说明: 
 * 作者: WChao 创建时间: 2017年8月21日 下午3:12:18
 */
public interface Decoder {
    Packet decode(ByteBuffer byteBuffer, int i, int i1, int i2, ChannelContext channelContext) throws TioDecodeException;
}
