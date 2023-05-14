package cn.starboot.tim.common.packet;

import cn.starboot.socket.Packet;
import cn.starboot.socket.utils.json.JsonUtil;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.common.packet.proto.RespPacketProto;

public class ImPacket extends Packet {

    private static final long serialVersionUID = -1586364817471781579L;

    // 消息命令码
    private TIMCommandType TIMCommandType;

    // 状态码
    private RespPacketProto.RespPacket.ImStatus imStatus;

    // 消息体
    private byte[] data;

    private ImPacket(TIMCommandType TIMCommandType, RespPacketProto.RespPacket.ImStatus imStatus, byte[] data) {
        this.TIMCommandType = TIMCommandType;
        this.imStatus = imStatus;
        this.data = data;
    }

	public static Builder newBuilder(){
		return new Builder();
	}

    public TIMCommandType getTIMCommandType() {
        return TIMCommandType;
    }

    public ImPacket setTIMCommandType(TIMCommandType TIMCommandType) {
        this.TIMCommandType = TIMCommandType;
        return this;
    }

	public RespPacketProto.RespPacket.ImStatus getImStatus() {
		return imStatus;
	}

	public void setImStatus(RespPacketProto.RespPacket.ImStatus imStatus) {
		this.imStatus = imStatus;
	}

	public byte[] getData() {
        return data;
    }

    public ImPacket setData(byte[] data) {
        this.data = data;
        return this;
    }

	public static class Builder extends Packet.Builder<ImPacket, Builder> {

		private TIMCommandType TIMCommandType;

		private RespPacketProto.RespPacket.ImStatus imStatus;

		private byte[] data;

		private Builder() {
		}

		public Builder setTIMCommandType(TIMCommandType TIMCommandType) {
			this.TIMCommandType = TIMCommandType;
			return this;
		}

		public Builder setImStatus(RespPacketProto.RespPacket.ImStatus imStatus) {
			this.imStatus = imStatus;
			return this;
		}

		public Builder setData(byte[] data) {
			this.data = data;
			return this;
		}

		@Override
		protected Builder getThis() {
			return this;
		}

		@Override
		public ImPacket build(){
			return new ImPacket(TIMCommandType, imStatus, data);
		}
	}
}
