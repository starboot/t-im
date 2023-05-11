package cn.starboot.tim.common.command;

/**
 * TIM消息响应命令码
 *
 * @author MDong
 */
public enum ReqClientCommandType {

    // 鉴权响应命令码
    COMMAND_AUTH_RESP(200,    "鉴权响应命令码"),

    // 聊天响应命令码
    COMMAND_CHAT_REQ(201,    "聊天响应命令码"),

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
    ReqClientCommandType(int code, String msg) {
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
    public static ReqClientCommandType getCommandTypeByCode(int code) {
        ReqClientCommandType reqClientCommandType = null;
        switch (code) {
            case 200: reqClientCommandType = COMMAND_AUTH_RESP;    break;
            case 201: reqClientCommandType = COMMAND_CHAT_REQ;     break;
            case 202: reqClientCommandType = COMMAND_LOGIN_RESP;   break;
            case 203: reqClientCommandType = COMMAND_BIND_RESP;    break;
            case 204: reqClientCommandType = COMMAND_HEART_RESP;   break;
            case 205: reqClientCommandType = COMMAND_CLOSE_RESP;   break;
            case 206: reqClientCommandType = COMMAND_ACK_RESP;     break;
            case 207: reqClientCommandType = COMMAND_USERS_RESP;   break;
            case 208: reqClientCommandType = COMMAND_MESSAGE_RESP; break;
        }
        return reqClientCommandType;
    }
}
