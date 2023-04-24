package cn.starboot.tim.client;

import cn.starboot.socket.Packet;
import cn.starboot.socket.StateMachineEnum;
import cn.starboot.socket.core.ChannelContext;
import cn.starboot.tim.client.command.ClientAbstractCmdHandler;
import cn.starboot.tim.client.command.CommandManager;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.codec.TIMPrivateTcpProtocol;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.command.ReqCommandType;
import cn.starboot.tim.common.packet.ImPacket;
import com.google.protobuf.InvalidProtocolBufferException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImClientProtocolHandler extends TIMPrivateTcpProtocol {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImClientProtocolHandler.class);

    // TIM系统定制私有TCP通讯协议的服务器对象
    private static ImClientProtocolHandler imServerProtocolHandler;

    // 此对象不让用户自己实例化
    private ImClientProtocolHandler() {
    }

    // 采用面向对象设计模式的单例模式创建
    public static TIMPrivateTcpProtocol getInstance() {
        if (imServerProtocolHandler == null){
            imServerProtocolHandler = new ImClientProtocolHandler();
        }
        return imServerProtocolHandler;
    }



    @Override
    public Packet handle(ChannelContext channelContext, Packet packet) {
        if (packet instanceof ImPacket) {
            // 消息处理
            ImPacket imPacket = (ImPacket) packet;
            ReqCommandType reqCommandType = imPacket.getReqCommandType();
            ClientAbstractCmdHandler cmdHandler = CommandManager.getCommand(reqCommandType);
            ImChannelContext imChannelContext = new ImChannelContext(channelContext);
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
