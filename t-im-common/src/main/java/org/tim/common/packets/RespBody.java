
package org.tim.common.packets;



import cn.hutool.core.util.ObjectUtil;
import org.tim.common.Status;
import org.tim.common.util.json.JsonKit;

import java.io.Serializable;

/**
 * Created by DELL(mxd) on 2021/12/23 11:57
 */
public class RespBody extends Message implements Serializable{

	private static final long serialVersionUID = 4470096592138927026L;
	/**
	 * 响应状态码;
	 */
	protected Integer code;
	/**
	 * 响应状态信息提示;
	 */
	protected String msg;
	/**
	 * 响应cmd命令码;
	 */
	protected Command command;
	/**
	 * 响应数据;
	 */
	protected Object data;

	public RespBody(){}

	public RespBody(Command command){
		this.command = command;
	}

	public RespBody(Command command,Object data){
		this(command);
		this.data = data;
	}

	public RespBody(Command command , Status status){
		this(status);
		this.command = command;
	}

	public RespBody(Status status){
		if(ObjectUtil.isNotEmpty(status)){
			this.code = status.getCode();
			this.msg = status.getMsg();
		}
	}

	public RespBody(Integer code, String msg){
		this.code = code;
		this.msg = msg;
	}

	public Integer getCode() {
		return code;
	}

	public RespBody setCode(Integer code) {
		this.code = code;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public RespBody setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public Command getCommand() {
		return command;
	}

	public RespBody setCommand(Command command) {
		this.command = command;
		return this;
	}

	public Object getData() {
		return data;
	}

	public RespBody setData(Object data) {
		this.data = data;
		return this;
	}

	@Override
	public String toString() {
		return "RespBody{" +
				"code=" + code +
				", msg='" + msg + '\'' +
				", command=" + command +
				", data=" + data +
				'}';
	}

	public byte[] toByte(){
		return JsonKit.toJsonBytes(this);
	}

}
