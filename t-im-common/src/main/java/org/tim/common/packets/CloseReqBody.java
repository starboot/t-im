package org.tim.common.packets;

/**
 * Created by DELL(mxd) on 2021/12/23 11:57
 */
public class CloseReqBody extends Message {

	private static final long serialVersionUID = 771895783302296339L;

	public CloseReqBody(){};

	public CloseReqBody(String userId){
		this.userId = userId;
	}

	/**
	 * 用户ID
	 */
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
