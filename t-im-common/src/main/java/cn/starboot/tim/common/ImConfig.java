package cn.starboot.tim.common;

import cn.starboot.socket.core.AioConfig;
import cn.starboot.tim.common.factory.ImPacketFactory;
import cn.starboot.tim.common.intf.TIMProcessor;
import cn.starboot.tim.common.packet.ImPacket;

/**
 * Created by DELL(mxd) on 2022/1/6 17:07
 */
public abstract class ImConfig<P extends TIMProcessor> {

	public static final String name = "TIM";

	public static final String version = "3.0.0.v20221001-RELEASE";

	public static final String charset = "utf-8";

	public static final String authKey = "authKey";

	/**
	 * 开启SSL
	 */
	public static boolean isUseSSL;

	private boolean ackPlugin;

	private final AioConfig aioConfig;

	private final ImPacketFactory imPacketFactory = (timCommandType, imStatus, data) -> ImPacket.newBuilder().setTIMCommandType(timCommandType).setImStatus(imStatus).setData(data).build();

	public ImConfig(AioConfig aioConfig) {
		this.aioConfig = aioConfig;
	}

	public AioConfig getAioConfig() {
		return this.aioConfig;
	}

	public boolean isServer() {
		return this.aioConfig.isServer();
	}

	public ImPacketFactory getImPacketFactory() {
		return imPacketFactory;
	}

	public abstract P getProcessor();

	public String getHost() {
		return this.aioConfig.getHost();
	}

	public int getPort() {
		return this.aioConfig.getPort();
	}

	public boolean isAckPlugin() {
		return ackPlugin;
	}

	public void setAckPlugin(boolean ackPlugin) {
		this.ackPlugin = ackPlugin;
	}

	@Override
	public String toString() {
		return "ImConfig{" +
				"ackPlugin=" + ackPlugin +
				'}';
	}
}
