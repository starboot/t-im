package cn.starboot.tim.common.entity;

import cn.starboot.tim.common.packet.proto.ChatPacketProto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL(mxd) on 2021/12/23 11:57
 */
public class HistoryMessage extends Message implements Serializable{

	/**
	 * 自行添加
	 */
	private static final long serialVersionUID = -3522175124687292440L;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 好友消息
	 */
	private Map<String,List<ChatPacketProto.ChatPacket>> friends;

	/**
	 * 群组消息
	 */
	private Map<String,List<ChatPacketProto.ChatPacket>> groups;

	private HistoryMessage(String userId, Map<String, List<ChatPacketProto.ChatPacket>> friends, Map<String, List<ChatPacketProto.ChatPacket>> groups) {
		super(null);
		this.userId = userId;
		this.friends = friends;
		this.groups = groups;
	}

	public static Builder newBuilder(){
		return new Builder();
	}

	public Map<String, List<ChatPacketProto.ChatPacket>> getFriends() {
		return friends;
	}

	public void setFriends(Map<String, List<ChatPacketProto.ChatPacket>> friends) {
		this.friends = friends;
	}

	public Map<String, List<ChatPacketProto.ChatPacket>> getGroups() {
		return groups;
	}

	public void setGroups(Map<String, List<ChatPacketProto.ChatPacket>> groups) {
		this.groups = groups;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public static class Builder extends Message.Builder<HistoryMessage, Builder> {

		private String userId;

		private Map<String,List<ChatPacketProto.ChatPacket>> friends;

		private Map<String,List<ChatPacketProto.ChatPacket>> groups;

		public Builder setUserId(String userId) {
			this.userId = userId;
			return this;
		}

		public Builder setFriends(Map<String, List<ChatPacketProto.ChatPacket>> friends) {
			this.friends = friends;
			return this;
		}

		public Builder setGroups(Map<String, List<ChatPacketProto.ChatPacket>> groups) {
			this.groups = groups;
			return this;
		}

		private Builder() {
		}

		@Override
		protected Builder getThis() {
			return this;
		}

		@Override
		public HistoryMessage build() {
			return new HistoryMessage(userId, friends, groups);
		}
	}
}
