
package cn.starboot.tim.common.packets;
/**
 * Created by DELL(mxd) on 2021/12/23 11:57
 */
public class ExitGroupNotifyRespBody extends Message{

	private static final long serialVersionUID = -8296191285966449387L;

	private User user;
	private String group;
	
	public User getUser() {
		return user;
	}
	public ExitGroupNotifyRespBody setUser(User user) {
		this.user = user;
		return this;
	}
	public String getGroup() {
		return group;
	}
	public ExitGroupNotifyRespBody setGroup(String group) {
		this.group = group;
		return this;
	}
}
