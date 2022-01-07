package org.tim.common.packets;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL(mxd) on 2021/12/23 11:57
 */
public class UserMessageData implements Serializable{

	private static final long serialVersionUID = 3012795615915661685L;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 好友消息
	 */
	private Map<String,List<ChatBody>> friends = new HashMap<String, List<ChatBody>>();
	/**
	 * 群组消息
	 */
	private Map<String,List<ChatBody>> groups = new HashMap<String, List<ChatBody>>();
	
	public UserMessageData(){}

	public UserMessageData(String userId){
		this.userId = userId;
	}
	public Map<String, List<ChatBody>> getFriends() {
		return friends;
	}
	public void setFriends(Map<String, List<ChatBody>> friends) {
		this.friends = friends;
	}
	public Map<String, List<ChatBody>> getGroups() {
		return groups;
	}
	public void setGroups(Map<String, List<ChatBody>> groups) {
		this.groups = groups;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UserMessageData{" +
				"userId='" + userId + '\'' +
				", friends=" + friends +
				", groups=" + groups +
				'}';
	}
}
