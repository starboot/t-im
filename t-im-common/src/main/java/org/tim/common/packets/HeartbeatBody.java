
package org.tim.common.packets;

/**
 * Created by DELL(mxd) on 2021/12/23 11:57
 */
public class HeartbeatBody extends Message{
	
	private static final long serialVersionUID = -1773817279179288833L;
	private byte hbbyte;
	
	public HeartbeatBody(){}
	public HeartbeatBody(byte hbbyte){
		this.hbbyte = hbbyte;
	}
	public byte getHbbyte() {
		return hbbyte;
	}

	public HeartbeatBody setHbbyte(byte hbbyte) {
		this.hbbyte = hbbyte;
		return this;
	}
	
}
