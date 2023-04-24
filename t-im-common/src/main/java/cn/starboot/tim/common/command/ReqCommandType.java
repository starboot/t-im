package cn.starboot.tim.common.command;

/**
 * TIM系统内置命令状态码
 *
 * @author MDong
 */
public enum ReqCommandType {

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
	COMMAND_REQ_RESP(109,  "请求响应命令码");

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
    ReqCommandType(int code, String msg) {
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
    public static ReqCommandType getCommandTypeByCode(int code) {
        ReqCommandType reqCommandType = null;
        switch (code) {
            case 100: reqCommandType = COMMAND_AUTH_REQ;    break;
            case 101: reqCommandType = COMMAND_CHAT_REQ;    break;
            case 102: reqCommandType = COMMAND_LOGIN_REQ;   break;
            case 103: reqCommandType = COMMAND_BIND_REQ;    break;
            case 104: reqCommandType = COMMAND_HEART_REQ;   break;
            case 105: reqCommandType = COMMAND_CLOSE_REQ;   break;
            case 106: reqCommandType = COMMAND_ACK_REQ;     break;
            case 107: reqCommandType = COMMAND_USERS_REQ;   break;
            case 108: reqCommandType = COMMAND_MESSAGE_REQ; break;
			case 109: reqCommandType = COMMAND_REQ_RESP;    break;
        }
        return reqCommandType;
    }
}
