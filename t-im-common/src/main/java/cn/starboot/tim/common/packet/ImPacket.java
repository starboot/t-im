package cn.starboot.tim.common.packet;

import cn.starboot.socket.Packet;
import cn.starboot.socket.utils.json.JsonUtil;
import cn.starboot.tim.common.command.ReqServerCommandType;

public class ImPacket extends Packet {

    private static final long serialVersionUID = -1586364817471781579L;

    // 消息命令码
    private ReqServerCommandType reqServerCommandType;

    // 消息体
    private byte[] data;

    public ImPacket(ReqServerCommandType reqServerCommandType, byte[] data) {
        this.reqServerCommandType = reqServerCommandType;
        this.data = data;
    }

	public static Builder newBuilder(){
		return new Builder();
	}

    public ReqServerCommandType getReqServerCommandType() {
        return reqServerCommandType;
    }

    public ImPacket setReqServerCommandType(ReqServerCommandType reqServerCommandType) {
        this.reqServerCommandType = reqServerCommandType;
        return this;
    }

    public byte[] getData() {
        return data;
    }

    public ImPacket setData(byte[] data) {
        this.data = data;
        return this;
    }

	public static class Builder {

		private ReqServerCommandType reqServerCommandType;

		private byte[] data;

		private Builder() {
		}

		public Builder setReqServerCommandType(ReqServerCommandType reqServerCommandType) {
			this.reqServerCommandType = reqServerCommandType;
			return this;
		}

		public Builder setData(byte[] data) {
			this.data = data;
			return this;
		}

		protected Builder getThis() {
			return this;
		}

		public ImPacket build(){
			return new ImPacket(reqServerCommandType, data);
		}
	}

    @Override
    public String toString() {
        return JsonUtil.toJSONString(this);
    }
}
