package cn.starboot.tim.common.command;

/**
 * TIM系统内置命令状态码
 *
 * @author MDong
 */
public enum TIMCommandType {

    // 鉴权命令码
    COMMAND_AUTH_REQ(100,    "鉴权命令码"),

    // 聊天命令码
    COMMAND_CHAT_REQ(101,    "聊天命令码"),

    // 登录命令码
    COMMAND_LOGIN_REQ(102,   "登录命令码"),

    // 绑定、解绑命令码
    COMMAND_BIND_REQ(103,    "绑定、解绑命令码"),

    // 心跳命令码
    COMMAND_HEART_REQ(104,   "心跳命令码"),

    // 关闭命令码
    COMMAND_CLOSE_REQ(105,   "关闭命令码"),

    // ACK命令码
    COMMAND_ACK_REQ(106,     "ACK命令码"),

    // 获取用户情况
    COMMAND_USERS_REQ(107,   "获取用户情况"),

    // 获取持久化聊天消息
    COMMAND_MESSAGE_REQ(108, "获取持久化聊天消息"),

	// 请求响应命令码
	COMMAND_REQ_RESP(109,  "请求响应命令码"),

	// ----------------------------------------------------

	// 鉴权响应命令码
	COMMAND_AUTH_RESP(200,    "鉴权响应命令码"),

	// 聊天响应命令码
	COMMAND_CHAT_RESP(201,    "聊天响应命令码"),

	// 登录响应命令码
	COMMAND_LOGIN_RESP(202,   "登录响应命令码"),

	// 绑定、解绑响应命令码
	COMMAND_BIND_RESP(203,    "绑定、解绑响应命令码"),

	// 心跳响应命令码
	COMMAND_HEART_RESP(204,   "心跳响应命令码"),

	// 关闭响应命令码
	COMMAND_CLOSE_RESP(205,   "关闭响应命令码"),

	// ACK响应命令码
	COMMAND_ACK_RESP(206,     "ACK响应命令码"),

	// 获取用户情况响应
	COMMAND_USERS_RESP(207,   "获取用户情况响应"),

	// 获取持久化聊天消息响应
	COMMAND_MESSAGE_RESP(208, "获取持久化聊天消息响应");

    /**
     * 命令码数字索引
     */
    private final int code;

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
    TIMCommandType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 获取指定命令码的索引
     *
     * @return int 索引号
     */
    public int getCode() {
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
    public static TIMCommandType getCommandTypeByCode(int code) {
        TIMCommandType TIMCommandType = null;
        switch (code) {
            case 100: TIMCommandType = COMMAND_AUTH_REQ;    break;
            case 101: TIMCommandType = COMMAND_CHAT_REQ;    break;
            case 102: TIMCommandType = COMMAND_LOGIN_REQ;   break;
            case 103: TIMCommandType = COMMAND_BIND_REQ;    break;
            case 104: TIMCommandType = COMMAND_HEART_REQ;   break;
            case 105: TIMCommandType = COMMAND_CLOSE_REQ;   break;
            case 106: TIMCommandType = COMMAND_ACK_REQ;     break;
            case 107: TIMCommandType = COMMAND_USERS_REQ;   break;
            case 108: TIMCommandType = COMMAND_MESSAGE_REQ; break;
			case 109: TIMCommandType = COMMAND_REQ_RESP;    break;
			case 200: TIMCommandType = COMMAND_AUTH_RESP;   break;
			case 201: TIMCommandType = COMMAND_CHAT_RESP;   break;
			case 202: TIMCommandType = COMMAND_LOGIN_RESP;  break;
			case 203: TIMCommandType = COMMAND_BIND_RESP;   break;
			case 204: TIMCommandType = COMMAND_HEART_RESP;  break;
			case 205: TIMCommandType = COMMAND_CLOSE_RESP;  break;
			case 206: TIMCommandType = COMMAND_ACK_RESP;    break;
			case 207: TIMCommandType = COMMAND_USERS_RESP;  break;
			case 208: TIMCommandType = COMMAND_MESSAGE_RESP;break;
        }
        return TIMCommandType;
    }
}