package cn.starboot.tim.client;

import cn.starboot.socket.core.ChannelContext;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.ImConfig;

import java.util.concurrent.atomic.LongAdder;

public class ImClientChannelContext extends ImChannelContext<ImClientConfig> {

	private final ImClientConfig clientConfig;

	/**
	 * 当前通道上下文信息数, 收到的
	 */
	private int imInMessageTotal = 0;

	/**
	 * 输出的
	 */
	private int imOutMessageTotal = 0;

	public ImClientChannelContext(ChannelContext channelContext, ImClientConfig config) {
		super(channelContext, config.getMaximumInterval());
		this.clientConfig = config;
	}

	@Override
	public ImClientConfig getConfig() {
		return this.clientConfig;
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
}
