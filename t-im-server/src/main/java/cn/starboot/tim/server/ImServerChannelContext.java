package cn.starboot.tim.server;

import cn.starboot.socket.core.ChannelContext;
import cn.starboot.tim.common.ImChannelContext;

import java.util.concurrent.atomic.LongAdder;

/**
 * 服务器TIM通道上下文
 */
public class ImServerChannelContext extends ImChannelContext<ImServerConfig> {

	/**
	 * TIM 服务器配置信息
	 */
	private final ImServerConfig config;

	/**
	 * 当前通道上下文信息数
	 */
	private int imInMessageTotal = 0;

	private int imOutMessageTotal = 0;

	public ImServerChannelContext(ChannelContext channelContext, ImServerConfig config) {
		super(channelContext);
		this.config = config;
	}

	@Override
	public ImServerConfig getConfig() {
		return this.config;
	}

	public int getImInMessageTotal() {
		return imInMessageTotal;
	}

	public void addImInMessageTotal() {
		imInMessageTotal++;
	}

	public int getImOutMessageTotal() {
		return imOutMessageTotal;
	}

	public void addImOutMessageTotal() {
		imOutMessageTotal++;
	}

//	public void resetImMessageTotal() {
//		imInMessageTotal.add(-imInMessageTotal.longValue());
//	}
}
