
package org.tim.common.packets;

/**
 * 登陆命令请求包体
 * Created by DELL(mxd) on 2021/12/23 11:57
 */
public class LoginReqBody extends Message {

	private static final long serialVersionUID = 5844538417007417210L;
	/**
	 * 用户Id
	 */
	private String userId;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 登陆token
	 */
	private String token;
	
	public LoginReqBody(){}
	
	public LoginReqBody(String token){
		this.token = token;
		this.cmd = Command.COMMAND_LOGIN_REQ.getNumber();
	}

	public LoginReqBody(String userId,String password){
		this.userId = userId;
		this.password = password;
		this.cmd = Command.COMMAND_LOGIN_REQ.getNumber();
	}

	public LoginReqBody(String userId,String password,String token){
		this(userId,password);
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
