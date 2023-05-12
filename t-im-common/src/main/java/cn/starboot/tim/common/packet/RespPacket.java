package cn.starboot.tim.common.packet;

import cn.starboot.tim.common.Status;
import cn.starboot.tim.common.entity.Message;
import com.alibaba.fastjson2.JSONObject;

public class RespPacket extends Message {

	private Status status;

	public RespPacket(JSONObject extras, Status status) {
		super(extras);
		this.status = status;
	}

	public static Builder newBuilder(){
		return new Builder();
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public static class Builder extends Message.Builder<RespPacket, Builder> {

		private Status status;

		private Builder(){}

		public Status getStatus() {
			return status;
		}

		public Builder setStatus(Status status) {
			this.status = status;
			return getThis();
		}

		@Override
		protected Builder getThis() {
			return this;
		}

		@Override
		public RespPacket build() {
			return new RespPacket(extras, status);
		}
	}
}
