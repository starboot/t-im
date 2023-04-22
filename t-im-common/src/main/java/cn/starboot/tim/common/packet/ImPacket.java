package cn.starboot.tim.common.packet;

import cn.starboot.socket.Packet;

import java.util.Arrays;

public class ImPacket extends Packet {

    private static final long serialVersionUID = -1586364817471781579L;

    // 消息命令码
    private CommandType commandType;

    // 消息体
    private byte[] data;

    public ImPacket() {
    }

    public ImPacket(CommandType commandType, byte[] data) {
        this.commandType = commandType;
        this.data = data;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public ImPacket setCommandType(CommandType commandType) {
        this.commandType = commandType;
        return this;
    }

    public byte[] getData() {
        return data;
    }

    public ImPacket setData(byte[] data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "ImPacket{" +
                " commandType=" + commandType +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
