
package org.tim.common.packets;

/**
 * Created by DELL(mxd) on 2021/12/23 11:57
 */
public class AuthRespBody extends Message {

	private static final long serialVersionUID = -7997317787209941399L;
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
