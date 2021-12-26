
package org.tim.common.packets;


import org.tim.common.ImStatus;
import org.tim.common.Status;

/**
 * Created by DELL(mxd) on 2021/12/23 11:57
 */
public class JoinGroupNotifyRespBody extends RespBody {
	
	private static final long serialVersionUID = 3828976681110713803L;
	private User user;
	private String group;

	public JoinGroupNotifyRespBody(){
		super(Command.COMMAND_JOIN_GROUP_NOTIFY_RESP, null);
	}

	public JoinGroupNotifyRespBody(Integer code, String msg){
		super(Command.COMMAND_JOIN_GROUP_NOTIFY_RESP, null);
		this.code = code;
		this.msg = msg;
	}

	public JoinGroupNotifyRespBody(Status status){
		super(Command.COMMAND_JOIN_GROUP_NOTIFY_RESP,status);
	}

	public JoinGroupNotifyRespBody(Command command, Status status){
		super(command,status);
	}
	public User getUser() {
		return user;
	}

	public JoinGroupNotifyRespBody setUser(User user) {
		this.user = user;
		return this;
	}
	public String getGroup() {
		return group;
	}

	public JoinGroupNotifyRespBody setGroup(String group) {
		this.group = group;
		return this;
	}

	public static JoinGroupNotifyRespBody success(){
		return new JoinGroupNotifyRespBody(ImStatus.C10011);
	}

	public static JoinGroupNotifyRespBody failed(){
		return new JoinGroupNotifyRespBody(ImStatus.C10012);
	}

}
