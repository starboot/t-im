package cn.starboot.tim.common.packet;

/**
 * TIM系统内置命令状态码
 *
 * By MDong
 */
public enum CommandType {

    // 鉴权命令码
    COMMAND_AUTH(0,"鉴权命令码"),

    // 聊天命令码
    COMMAND_CHAT(1, "聊天命令码"),

    // 登录命令码
    COMMAND_LOGIN(2, "登录命令码"),

    // 绑定、解绑命令码
    COMMAND_BIND(3, "绑定、解绑命令码"),

    // 心跳命令码
    COMMAND_HEART(4, "心跳命令码"),

    // 关闭命令码
    COMMAND_CLOSE(5, "关闭命令码"),

    // ACK命令码
    COMMAND_ACK(6, "ACK命令码"),

    // 获取用户情况
    COMMAND_USERS(7, "获取用户情况"),

    // 获取持久化聊天消息
    COMMAND_MESSAGE(8, "获取持久化聊天消息");

    private final int code;

    private final String msg;

    CommandType(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static CommandType getCommandTypeByCode(int code) {
        CommandType commandType = null;
        switch (code) {
            case 0: commandType = COMMAND_AUTH;    break;
            case 1: commandType = COMMAND_CHAT;    break;
            case 2: commandType = COMMAND_LOGIN;   break;
            case 3: commandType = COMMAND_BIND;    break;
            case 4: commandType = COMMAND_HEART;   break;
            case 5: commandType = COMMAND_CLOSE;   break;
            case 6: commandType = COMMAND_ACK;     break;
            case 7: commandType = COMMAND_USERS;   break;
            case 8: commandType = COMMAND_MESSAGE; break;
        }
        return commandType;
    }
}
