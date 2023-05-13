
package cn.starboot.tim.common.entity;

import com.alibaba.fastjson2.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by DELL(mxd) on 2021/12/23 11:57
 */
public class Group extends Message {

	/**
	 * 自行添加UID
	 */
	private static final long serialVersionUID = -5652475498774105651L;

	/**
	 * 群组ID
	 */
	private String groupId;

	/**
	 * 群组名称
	 */
	private String name;

	/**
	 * 群组头像
	 */
	private String avatar;

	/**
	 * 在线人数
	 */
	private Integer online;

	/**
	 * 组用户
	 */
	private List<User> users;

	private Group(String groupId , String name, String avatar, Integer online, List<User> users, JSONObject extras){
		super(extras);
		this.groupId = groupId;
		this.name = name;
		this.avatar = avatar;
		this.online = online;
		this.users = users;
	}

	public static Builder newBuilder(){
		return new Builder();
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public static class Builder extends Message.Builder<Group, Builder>{

		/**
		 * 群组ID
		 */
		private String groupId;

		/**
		 * 群组名称
		 */
		private String name;

		/**
		 * 群组头像
		 */
		private String avatar;

		/**
		 * 在线人数
		 */
		private Integer online;

		/**
		 * 组用户
		 */
		private List<User> users = null;

		public Builder(){};

		public Builder groupId(String groupId) {
			this.groupId = groupId;
			return this;
		}
		public Builder name(String name) {
			this.name = name;
			return this;
		}
		public Builder avatar(String avatar) {
			this.avatar = avatar;
			return this;
		}
		public Builder setChatType(Integer online) {
			this.online = online;
			return this;
		}
		public Builder addUser(User user) {
			if(Objects.isNull(users) || users.size() == 0){
				users = new ArrayList<>();
			}
			users.add(user);
			return this;
		}
		@Override
		protected Builder getThis() {
			return this;
		}

		@Override
		public Group build(){
			return new Group(this.groupId , this.name , this.avatar , this.online , this.users, this.extras);
		}
	}

	@Override
	public Group clone() throws CloneNotSupportedException {
		super.clone();
		Group group = Group.newBuilder().build();
//		BeanUtil.copyProperties(this, group,"users");
		return group;
	}

}
