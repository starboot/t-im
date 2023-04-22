
package cn.starboot.tim.common.packets;


import cn.starboot.tim.common.ImStatus;
import cn.starboot.tim.common.Status;

/**
 * Created by DELL(mxd) on 2021/12/23 11:57
 */
public class  LoginRespBody extends RespBody {

	private static final long serialVersionUID = 7893136245530929211L;
	private String token;
	private User user;

	public LoginRespBody(){
		this.setCommand(Command.COMMAND_LOGIN_RESP);
	}

	public LoginRespBody(Status status){
		this(status,null);
	}

	public LoginRespBody(Status status , User user){
		this(status, user, null);
	}

	public LoginRespBody(Status status , User user, String token){
		super(Command.COMMAND_LOGIN_RESP, status);
		this.user = user;
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public static LoginRespBody success(){
		return new LoginRespBody(ImStatus.C10007);
	}

	public static LoginRespBody failed(){
		return new LoginRespBody(ImStatus.C10008);
	}

	public static LoginRespBody failed(String msg){
		LoginRespBody loginRespBody = new LoginRespBody(ImStatus.C10008);
		loginRespBody.setMsg(msg);
		return loginRespBody;
	}
}
