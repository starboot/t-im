
package org.tim.common.packets;

/**
 * Created by DELL(mxd) on 2021/12/23 11:57
 */
public class HandshakeBody extends Message{

	private static final long serialVersionUID = 4493254915372077140L;
	private byte hbyte;
	
	public HandshakeBody(){}
	public HandshakeBody(byte hbyte){
		this.hbyte = hbyte;
	}
	public byte getHbyte() {
		return hbyte;
	}

	public HandshakeBody setHbyte(byte hbyte) {
		this.hbyte = hbyte;
		return this;
	}
	
}
