package cn.starboot.tim.server.protocol.tcp;

import cn.hutool.core.util.ObjectUtil;
import cn.starboot.socket.Packet;
import cn.starboot.socket.core.ChannelContext;
import cn.starboot.socket.enums.StateMachineEnum;
import cn.starboot.tim.common.ImChannelContextFactory;
import cn.starboot.tim.common.codec.TIMPrivateTcpProtocol;
import cn.starboot.tim.common.command.handler.AbstractCmdHandler;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.command.ReqCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.server.ImServerChannelContext;
import cn.starboot.tim.server.command.CommandManager;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImServerProtocolHandler extends TIMPrivateTcpProtocol {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImServerProtocolHandler.class);

    // TIM系统定制私有TCP通讯协议的服务器对象
    private static ImServerProtocolHandler imServerProtocolHandler;

	private final ImChannelContextFactory<ImServerChannelContext> serverImChannelContextFactory;

    // 此对象不让用户自己实例化
    private ImServerProtocolHandler(ImChannelContextFactory<ImServerChannelContext> serverImChannelContextFactory) {
    	this.serverImChannelContextFactory = serverImChannelContextFactory;
    }

    // 采用面向对象设计模式的单例模式创建
    public synchronized static TIMPrivateTcpProtocol getInstance(ImChannelContextFactory<ImServerChannelContext> serverImChannelContextFactory) {
        if (imServerProtocolHandler == null){
            imServerProtocolHandler = new ImServerProtocolHandler(serverImChannelContextFactory);
        }

        return imServerProtocolHandler;
    }

    @Override
    public Packet handle(ChannelContext channelContext, Packet packet) {
		ImServerChannelContext imServerChannelContext = channelContext.getAttr(Key.IM_CHANNEL_CONTEXT_KEY, ImServerChannelContext.class);
		if (ObjectUtil.isEmpty(imServerChannelContext)) {
			imServerChannelContext = serverImChannelContextFactory.createImChannelContext(channelContext);
			if (imServerChannelContext.getConfig() == null) {
				System.out.println("cn.starboot.tim.server.protocol.tcp.ImServerProtocolHandler：没有加载到配置文件...");
			}
			channelContext.attr(Key.IM_CHANNEL_CONTEXT_KEY, imServerChannelContext);
		}
        if (packet instanceof ImPacket) {
            // 消息处理
            ImPacket imPacket = (ImPacket) packet;
            ReqCommandType reqCommandType = imPacket.getReqCommandType();
            AbstractCmdHandler cmdHandler = CommandManager.getCommand(reqCommandType);
            try {
                cmdHandler.handler(imPacket, imServerChannelContext);
            } catch (ImException | InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
        }else {
            LOGGER.error("收到异常包裹");
        }
        return null;

    }

    @Override
    public void stateEvent(ChannelContext channelContext, StateMachineEnum stateMachineEnum, Throwable throwable) {

    }
}
