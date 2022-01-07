package org.tim.common;

import org.tim.common.packets.Command;
import org.tio.core.intf.Packet;

/**
 * Created by DELL(mxd) on 2021/12/23 20:25
 */
public class ImPacket extends Packet {

    private static final long serialVersionUID = 2860124478985962940L;
    /**
     * 包状态码;
     */
    protected Status status;
    /**
     * 消息体;
     */
    protected byte[] body;
    /**
     * 消息命令;
     */
    private Command command;

    public static ImPacket newPacket(byte[] body){
        ImPacket packet = new ImPacket();
        packet.setBody(body);
        return packet;
    }

    public ImPacket(Status status, byte[] body, Command command) {
        this.status = status;
        this.body = body;
        this.command = command;
    }

    public ImPacket() {
    }

    public ImPacket(Command command, byte[] body) {
        this.body = body;
        this.command = command;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void setBody(byte[] body){
        this.body = body;
    }

    public byte[] getBody() {
        return body;
    }

}
