package cn.starboot.tim.common.packet;

/**
 * TIM消息响应命令码
 *
 * @author MDong
 */
public enum RespCommandType {

    // 鉴权命令码
    COMMAND_AUTH_RESP(200,    "鉴权命令码"),

    // 聊天命令码
    COMMAND_CHAT_RESP(201,    "聊天命令码"),

    // 登录命令码
    COMMAND_LOGIN_RESP(202,   "登录命令码"),

    // 绑定、解绑命令码
    COMMAND_BIND_RESP(203,    "绑定、解绑命令码"),

    // 心跳命令码
    COMMAND_HEART_RESP(204,   "心跳命令码"),

    // 关闭命令码
    COMMAND_CLOSE_RESP(205,   "关闭命令码"),

    // ACK命令码
    COMMAND_ACK_RESP(206,     "ACK命令码"),

    // 获取用户情况
    COMMAND_USERS_RESP(207,   "获取用户情况"),

    // 获取持久化聊天消息
    COMMAND_MESSAGE_RESP(208, "获取持久化聊天消息"),

    // 响应命令码
    COMMAND_RESP_RESP(209,    "响应命令码");

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
    RespCommandType(int code, String msg) {
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
    public static RespCommandType getCommandTypeByCode(int code) {
        RespCommandType respCommandType = null;
        switch (code) {
            case 200: respCommandType = COMMAND_AUTH_RESP;    break;
            case 201: respCommandType = COMMAND_CHAT_RESP;    break;
            case 202: respCommandType = COMMAND_LOGIN_RESP;   break;
            case 203: respCommandType = COMMAND_BIND_RESP;    break;
            case 204: respCommandType = COMMAND_HEART_RESP;   break;
            case 205: respCommandType = COMMAND_CLOSE_RESP;   break;
            case 206: respCommandType = COMMAND_ACK_RESP;     break;
            case 207: respCommandType = COMMAND_USERS_RESP;   break;
            case 208: respCommandType = COMMAND_MESSAGE_RESP; break;
            case 209: respCommandType = COMMAND_RESP_RESP;    break;
        }
        return respCommandType;
    }
}
