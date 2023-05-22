package cn.starboot.tim.client.protocol;

import cn.starboot.socket.Packet;
import cn.starboot.socket.core.ChannelContext;
import cn.starboot.socket.enums.StateMachineEnum;
import cn.starboot.tim.client.ImClientChannelContext;
import cn.starboot.tim.client.ImClientConfig;
import cn.starboot.tim.client.command.handler.AbstractClientCmdHandler;
import cn.starboot.tim.client.command.TIMClientCommandManager;
import cn.starboot.tim.client.handler.ImClientPacketProtocolHandler;
import cn.starboot.tim.common.codec.PrivateTcpProtocol;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.factory.ImChannelContextFactory;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.common.util.TIMLogUtil;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientPrivateTcpProtocol extends PrivateTcpProtocol {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientPrivateTcpProtocol.class);

    // TIM系统定制私有TCP通讯协议的服务器对象
    private static ClientPrivateTcpProtocol imServerProtocolHandler;


	private final ImChannelContextFactory<ImClientChannelContext> clientImChannelContextFactory;

	private final ImClientPacketProtocolHandler imClientPacketProtocolHandler;

    // 此对象不让用户自己实例化
    private ClientPrivateTcpProtocol(ImChannelContextFactory<ImClientChannelContext> clientImChannelContextFactory,
									 ImClientPacketProtocolHandler imClientPacketProtocolHandler) {
		super(new ImClientPacketProtocolHandler());
		this.imClientPacketProtocolHandler = imClientPacketProtocolHandler;
		this.clientImChannelContextFactory = clientImChannelContextFactory;
	}

    // 采用面向对象设计模式的单例模式创建
    public static PrivateTcpProtocol getInstance(ImChannelContextFactory<ImClientChannelContext> clientImChannelContextFactory,
												 ImClientPacketProtocolHandler imClientPacketProtocolHandler) {
        if (imServerProtocolHandler == null){
            imServerProtocolHandler = new ClientPrivateTcpProtocol(clientImChannelContextFactory, imClientPacketProtocolHandler);
        }
        return imServerProtocolHandler;
    }



    @Override
    public Packet handle(ChannelContext channelContext, Packet packet) {
		ImClientChannelContext imClientChannelContext = channelContext.getAttr(Key.IM_CHANNEL_CONTEXT_KEY, ImClientChannelContext.class);
		if (imClientChannelContext == null) {
			imClientChannelContext = clientImChannelContextFactory.createImChannelContext(channelContext);
			if (imClientChannelContext.getConfig() == null) {
				TIMLogUtil.error(LOGGER, "cn.starboot.tim.client.protocol.ClientPrivateTcpProtocol：not find tim.properties ...");
			}
			channelContext.attr(Key.IM_CHANNEL_CONTEXT_KEY, imClientChannelContext);
		}
        if (packet instanceof ImPacket) {
            // 消息处理
            ImPacket imPacket = (ImPacket) packet;
            return this.imClientPacketProtocolHandler.handle(imClientChannelContext, imPacket);

        }else {
            LOGGER.error("收到异常包裹");
        }
        return null;
    }

	@Override
	public void stateEvent(ChannelContext channelContext, StateMachineEnum stateMachineEnum, Throwable throwable) {
		if (stateMachineEnum == StateMachineEnum.DECODE_EXCEPTION || stateMachineEnum == StateMachineEnum.PROCESS_EXCEPTION) {
			throwable.printStackTrace();
		}
	}
}
