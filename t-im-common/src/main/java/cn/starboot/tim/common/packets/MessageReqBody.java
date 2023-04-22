package cn.starboot.tim.common.packets;

/**
 * Created by DELL(mxd) on 2021/12/23 11:57
 */
public class MessageReqBody extends Message {

	private static final long serialVersionUID = 415241878119837657L;
	/**
	 * 发送用户id;
	 */
	private String fromUserId;
	/**
	 * 接收用户id;
	 */
	private String userId;
	/**
	 * 群组id;
	 */
	private String groupId;
	/**
	 * 0:离线消息,1:历史消息;
	 */
	private Integer type;
	/**
	 * 消息时间;
	 */
	private String timelineId;



	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTimelineId() {
		return timelineId;
	}

	public void setTimelineId(String timelineId) {
		this.timelineId = timelineId;
	}
}
