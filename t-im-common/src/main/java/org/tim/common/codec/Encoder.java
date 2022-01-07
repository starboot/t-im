
package org.tim.common.codec;

import org.tio.core.ChannelContext;
import org.tio.core.TioConfig;
import org.tio.core.intf.Packet;

import java.nio.ByteBuffer;

/**
 * 版本: [1.0]
 * 功能说明: 
 * 作者: WChao 创建时间: 2017年8月21日 下午3:12:35
 */
public interface Encoder {

    ByteBuffer encode(Packet packet, TioConfig tioConfig, ChannelContext channelContext);
}
