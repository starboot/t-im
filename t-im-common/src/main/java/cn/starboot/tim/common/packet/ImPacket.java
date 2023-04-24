package cn.starboot.tim.common.packet;

import cn.starboot.socket.Packet;

import java.util.Arrays;

public class ImPacket extends Packet {

    private static final long serialVersionUID = -1586364817471781579L;

    // 消息命令码
    private ReqCommandType reqCommandType;

    // 消息体
    private byte[] data;

    public ImPacket() {
    }

    public ImPacket(ReqCommandType reqCommandType, byte[] data) {
        this.reqCommandType = reqCommandType;
        this.data = data;
    }

    public ReqCommandType getReqCommandType() {
        return reqCommandType;
    }

    public ImPacket setReqCommandType(ReqCommandType reqCommandType) {
        this.reqCommandType = reqCommandType;
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
                " commandType=" + reqCommandType +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
