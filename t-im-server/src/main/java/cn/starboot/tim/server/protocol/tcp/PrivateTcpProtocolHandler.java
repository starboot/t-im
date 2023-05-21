package cn.starboot.tim.server.protocol.tcp;

import cn.hutool.core.util.ObjectUtil;
import cn.starboot.socket.Packet;
import cn.starboot.socket.core.ChannelContext;
import cn.starboot.socket.enums.StateMachineEnum;
import cn.starboot.tim.common.factory.ImChannelContextFactory;
import cn.starboot.tim.common.codec.PrivateTcpProtocol;
import cn.starboot.tim.common.command.handler.AbstractCmdHandler;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.plugin.TIMPlugin;
import cn.starboot.tim.common.util.TIMLogUtil;
import cn.starboot.tim.server.ImServerChannelContext;
import cn.starboot.tim.server.ImServerConfig;
import cn.starboot.tim.server.command.TIMServerCommandManager;
import cn.starboot.tim.server.intf.TIMServerProcessor;
import cn.starboot.tim.server.protocol.ImServerPacketProtocolHandler;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrivateTcpProtocolHandler extends PrivateTcpProtocol {

    private static final Logger LOGGER = LoggerFactory.getLogger(PrivateTcpProtocolHandler.class);

    // TIM系统定制私有TCP通讯协议的服务器对象
    private static PrivateTcpProtocolHandler privateTcpProtocolHandler;

	private final ImChannelContextFactory<ImServerChannelContext> serverImChannelContextFactory;

	private final ImServerPacketProtocolHandler imServerPacketProtocolHandler;

    // 此对象不让用户自己实例化
    private PrivateTcpProtocolHandler(ImChannelContextFactory<ImServerChannelContext> serverImChannelContextFactory,
									  ImServerPacketProtocolHandler imServerPacketProtocolHandler) {
		super(imServerPacketProtocolHandler);
		this.imServerPacketProtocolHandler = imServerPacketProtocolHandler;
		this.serverImChannelContextFactory = serverImChannelContextFactory;

    }

    // 采用面向对象设计模式的单例模式创建
    public synchronized static PrivateTcpProtocol getInstance(ImChannelContextFactory<ImServerChannelContext> serverImChannelContextFactory,
															  ImServerPacketProtocolHandler imServerPacketProtocolHandler) {
        if (privateTcpProtocolHandler == null){
            privateTcpProtocolHandler = new PrivateTcpProtocolHandler(serverImChannelContextFactory, imServerPacketProtocolHandler);
        }
        return privateTcpProtocolHandler;
    }

    @Override
    public Packet handle(ChannelContext channelContext, Packet packet) {
		ImServerChannelContext imServerChannelContext = channelContext.getAttr(Key.IM_CHANNEL_CONTEXT_KEY, ImServerChannelContext.class);
		if (ObjectUtil.isEmpty(imServerChannelContext)) {
			imServerChannelContext = serverImChannelContextFactory.createImChannelContext(channelContext);
			if (imServerChannelContext.getConfig() == null) {
				TIMLogUtil.error(LOGGER, "cn.starboot.tim.server.protocol.tcp.ImServerProtocolHandler：not find tim.properties ...");
			}
			channelContext.attr(Key.IM_CHANNEL_CONTEXT_KEY, imServerChannelContext);
		}
        if (packet instanceof ImPacket) {
            // 消息处理
            ImPacket imPacket = (ImPacket) packet;
            // 二层协议处理
			return this.imServerPacketProtocolHandler.handle(imServerChannelContext, imPacket);
        }else {
			TIMLogUtil.error(LOGGER, "cn.starboot.tim.server.protocol.tcp.ImServerProtocolHandler：收到异常包裹...");
        }
        return null;

    }

    @Override
    public void stateEvent(ChannelContext channelContext, StateMachineEnum stateMachineEnum, Throwable throwable) {

    }
}
