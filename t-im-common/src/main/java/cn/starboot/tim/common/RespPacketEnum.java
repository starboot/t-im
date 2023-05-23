package cn.starboot.tim.common;

public enum RespPacketEnum {

	NONE((byte) 1, "")

	;
	/**
	 * 命令码数字索引
	 */
	private final byte code;

	/**
	 * msg
	 */
	private final String msg;

	/**
	 * 枚举构造
	 *
	 * @param code 索引
	 * @param msg  msg
	 */
	RespPacketEnum(byte code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	/**
	 * 获取指定命令码的索引
	 *
	 * @return int 索引号
	 */
	public byte getCode() {
		return code;
	}

	/**
	 * 获取解释说明
	 *
	 * @return msg
	 */
	public String getMsg() {
		return msg;
	}

}
