package org.tim.client.intf;

import org.tio.core.intf.Packet;

/**
 * Created by DELL(mxd) on 2022/1/14 15:16
 */
public class ACKPacket extends Packet{

    private static final long serialVersionUID = 6601914933960584929L;

    private Integer ack;

    private Packet packet;

    public ACKPacket(Integer ack, Packet packet) {
        this.ack = ack;
        this.packet = packet;
    }

    public Integer getAck() {
        return ack;
    }

    public void setAck(Integer ack) {
        this.ack = ack;
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}
