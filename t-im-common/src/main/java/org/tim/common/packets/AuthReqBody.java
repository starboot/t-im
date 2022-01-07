
package org.tim.common.packets;

/**
 * Created by DELL(mxd) on 2021/12/23 11:57
 */
public class AuthReqBody extends Message {


	private static final long serialVersionUID = 7920485353727443465L;
	private String token;//token验证;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
