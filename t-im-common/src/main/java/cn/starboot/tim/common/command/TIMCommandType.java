package cn.starboot.tim.common.command;

/**
 * TIM系统内置命令状态码
 *
 * @author MDong
 */
public enum TIMCommandType {

    // 鉴权命令码
    COMMAND_AUTH_REQ((byte) -1,    "鉴权命令码"),

    // 聊天命令码
    COMMAND_CHAT_REQ((byte) -2,    "聊天命令码"),

    // 登录命令码
    COMMAND_LOGIN_REQ((byte) -3,   "登录命令码"),

    // 绑定、解绑命令码
    COMMAND_BIND_REQ((byte) -4,    "绑定、解绑命令码"),

    // 心跳命令码
    COMMAND_HEART_REQ((byte) -5,   "心跳命令码"),

    // 关闭命令码
    COMMAND_CLOSE_REQ((byte) -6,   "关闭命令码"),

    // ACK命令码
    COMMAND_ACK_REQ((byte) -7,     "ACK命令码"),

    // 获取用户情况
    COMMAND_USERS_REQ((byte) -8,   "获取用户情况"),

    // 获取持久化聊天消息
    COMMAND_MESSAGE_REQ((byte) -9, "获取持久化聊天消息"),

	// 请求响应命令码
	COMMAND_REQ_RESP((byte) -10,  "请求响应命令码"),

	// ----------------------------------------------------

	// 鉴权响应命令码
	COMMAND_AUTH_RESP((byte) 0,    "鉴权响应命令码"),

	// 聊天响应命令码
	COMMAND_CHAT_RESP((byte) 1,    "聊天响应命令码"),

	// 登录响应命令码
	COMMAND_LOGIN_RESP((byte) 2,   "登录响应命令码"),

	// 绑定、解绑响应命令码
	COMMAND_BIND_RESP((byte) 3,    "绑定、解绑响应命令码"),

	// 心跳响应命令码
	COMMAND_HEART_RESP((byte) 4,   "心跳响应命令码"),

	// 关闭响应命令码
	COMMAND_CLOSE_RESP((byte) 5,   "关闭响应命令码"),

	// ACK响应命令码
	COMMAND_ACK_RESP((byte) 6,     "ACK响应命令码"),

	// 获取用户情况响应
	COMMAND_USERS_RESP((byte) 7,   "获取用户情况响应"),

	// 获取持久化聊天消息响应
	COMMAND_MESSAGE_RESP((byte) 8, "获取持久化聊天消息响应");

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
    TIMCommandType(byte code, String msg) {
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

    /**
     * 根据数字索引获取命令类型枚举类
     *
     * @param code 索引数字
     * @return     CommandType
     */
    public static TIMCommandType getCommandTypeByCode(byte code) {
        TIMCommandType TIMCommandType = null;
        switch (code) {
            case -1:  TIMCommandType = COMMAND_AUTH_REQ;    break;
            case -2:  TIMCommandType = COMMAND_CHAT_REQ;    break;
            case -3:  TIMCommandType = COMMAND_LOGIN_REQ;   break;
            case -4:  TIMCommandType = COMMAND_BIND_REQ;    break;
            case -5:  TIMCommandType = COMMAND_HEART_REQ;   break;
            case -6:  TIMCommandType = COMMAND_CLOSE_REQ;   break;
            case -7:  TIMCommandType = COMMAND_ACK_REQ;     break;
            case -8:  TIMCommandType = COMMAND_USERS_REQ;   break;
            case -9:  TIMCommandType = COMMAND_MESSAGE_REQ; break;
			case -10: TIMCommandType = COMMAND_REQ_RESP;    break;
			case  0:  TIMCommandType = COMMAND_AUTH_RESP;   break;
			case  1:  TIMCommandType = COMMAND_CHAT_RESP;   break;
			case  2:  TIMCommandType = COMMAND_LOGIN_RESP;  break;
			case  3:  TIMCommandType = COMMAND_BIND_RESP;   break;
			case  4:  TIMCommandType = COMMAND_HEART_RESP;  break;
			case  5:  TIMCommandType = COMMAND_CLOSE_RESP;  break;
			case  6:  TIMCommandType = COMMAND_ACK_RESP;    break;
			case  7:  TIMCommandType = COMMAND_USERS_RESP;  break;
			case  8:  TIMCommandType = COMMAND_MESSAGE_RESP;break;
        }
        return TIMCommandType;
    }
}
