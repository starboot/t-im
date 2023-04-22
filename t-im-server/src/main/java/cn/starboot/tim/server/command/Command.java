package cn.starboot.tim.server.command;


import cn.starboot.tim.common.packet.CommandType;

/**
 * 命令和处理器映射关系枚举类
 *
 * Created by DELL(mxd) on 2022/9/6 12:28
 */
public enum Command {

    // ACK同步消息命令处理器
    ACKReqHandler(CommandType.COMMAND_ACK, "org.tim.server.command.handler.ACKReqHandler"),

    // 鉴权命令处理器
    AuthReqHandler(CommandType.COMMAND_AUTH, "org.tim.server.command.handler.AuthReqHandler"),

    // 消息命令处理器
    ChatReqHandler(CommandType.COMMAND_CHAT, "org.tim.server.command.handler.ChatReqHandler"),

    // 关闭连接命令处理器
    CloseReqHandler(CommandType.COMMAND_CLOSE, "org.tim.server.command.handler.CloseReqHandler"),

    // 心跳命令处理器
    HeartbeatReqHandler(CommandType.COMMAND_HEART, "org.tim.server.command.handler.HeartbeatReqHandler"),

    // 绑定命令处理器
    BindReqHandler(CommandType.COMMAND_BIND, "org.tim.server.command.handler.BindReqHandler"),

    // 登录命令处理器
    LoginReqHandler(CommandType.COMMAND_LOGIN, "org.tim.server.command.handler.LoginReqHandler"),

    // 可以实时获取平台用户状态的命令处理器
    UserReqHandler(CommandType.COMMAND_USERS, "org.tim.server.command.handler.UserReqHandler"),

    // 获取消息记录命令处理器
    MessageReqHandlerCommandType(CommandType.COMMAND_MESSAGE, "org.tim.server.command.handler.MessageReqHandler");

    private final CommandType cmd;

    private final String path;

    Command(CommandType cmd, String path) {
        this.cmd = cmd;
        this.path = path;
    }

    public CommandType getCmd() {
        return cmd;
    }

    public String getPath() {
        return path;
    }

    public static Command valueOf(CommandType cmd) {
        return forNumber(cmd);
    }

    public static Command forNumber(CommandType cmd) {
        for(Command command : Command.values()){
            if(command.getCmd() == cmd){
                return command;
            }
        }
        return null;
    }
}
