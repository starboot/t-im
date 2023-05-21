package cn.starboot.tim.common;

import cn.starboot.socket.core.ChannelContext;
import cn.starboot.tim.common.intf.TIMProcessor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by DELL(mxd) on 2022/9/4 17:02
 */
public abstract class ImChannelContext<E extends ImConfig<? extends TIMProcessor>> {

	private static final Integer initialValue = 0;

	private final AtomicInteger reqInteger = new AtomicInteger(initialValue);

	private final AtomicInteger respInteger = new AtomicInteger(initialValue);

	/**
	 * 通讯对象
	 */
    private final ChannelContext channelContext;

	public ImChannelContext(ChannelContext channelContext) {
		this.channelContext = channelContext;
	}

	public ChannelContext getChannelContext() {
        return this.channelContext;
    }

	public abstract E getConfig();

	public String getImChannelContextId() {
		return this.channelContext.getId();
	}

	public void setImChannelContextId(String userId) {
		this.channelContext.setId(userId);
	}

	public void setAttr(String key, Object o) {
		this.channelContext.attr(key, o);
	}

	public <T> T getAttr(String key, Class<T> clazz) {
		return this.channelContext.getAttr(key, clazz);
	}

	public Integer getReqInteger() {
		return reqInteger.get();
	}

	public Integer getRespInteger() {
		return respInteger.get();
	}
}
