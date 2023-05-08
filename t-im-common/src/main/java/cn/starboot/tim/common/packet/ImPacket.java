package cn.starboot.tim.common.packet;

import cn.starboot.socket.Packet;
import cn.starboot.socket.utils.json.JsonUtil;
import cn.starboot.tim.common.command.ReqCommandType;

public class ImPacket extends Packet {

    private static final long serialVersionUID = -1586364817471781579L;

    // 消息命令码
    private ReqCommandType reqCommandType;

    // 消息体
    private byte[] data;

    public ImPacket(ReqCommandType reqCommandType, byte[] data) {
        this.reqCommandType = reqCommandType;
        this.data = data;
    }

	public static Builder newBuilder(){
		return new Builder();
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

	public static class Builder {

		private ReqCommandType reqCommandType;

		private byte[] data;

		private Builder() {
		}

		public Builder setReqCommandType(ReqCommandType reqCommandType) {
			this.reqCommandType = reqCommandType;
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
			return new ImPacket(reqCommandType, data);
		}
	}

    @Override
    public String toString() {
        return JsonUtil.toJSONString(this);
    }
}
