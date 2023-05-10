package cn.starboot.tim.server.protocol.tcp;

import cn.starboot.socket.Packet;
import cn.starboot.socket.core.ChannelContext;
import cn.starboot.socket.enums.StateMachineEnum;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.codec.TIMPrivateTcpProtocol;
import cn.starboot.tim.common.command.handler.AbstractCmdHandler;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.command.ReqCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import cn.starboot.tim.server.ImServerChannelContext;
import cn.starboot.tim.server.ImServerConfig;
import cn.starboot.tim.server.command.CommandManager;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImServerProtocolHandler extends TIMPrivateTcpProtocol {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImServerProtocolHandler.class);

    // TIM系统定制私有TCP通讯协议的服务器对象
    private static ImServerProtocolHandler imServerProtocolHandler;

    // 此对象不让用户自己实例化
    private ImServerProtocolHandler() {
    }

    // 采用面向对象设计模式的单例模式创建
    public synchronized static TIMPrivateTcpProtocol getInstance() {
        if (imServerProtocolHandler == null){
            imServerProtocolHandler = new ImServerProtocolHandler();
        }
        return imServerProtocolHandler;
    }

    @Override
    public Packet handle(ChannelContext channelContext, Packet packet) {
        if (packet instanceof ImPacket) {
            // 消息处理
            ImPacket imPacket = (ImPacket) packet;
            ReqCommandType reqCommandType = imPacket.getReqCommandType();
            AbstractCmdHandler cmdHandler = CommandManager.getCommand(reqCommandType);
            ImChannelContext imChannelContext = new ImServerChannelContext(channelContext, new ImServerConfig(null));
            try {
                cmdHandler.handler(imPacket, imChannelContext);
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
