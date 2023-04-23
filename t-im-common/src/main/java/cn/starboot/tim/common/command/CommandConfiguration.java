package cn.starboot.tim.common.command;

import cn.starboot.tim.common.packet.CommandType;

/**
 * Created by DELL(mxd) on 2021/12/24 16:44
 */
public class CommandConfiguration {

    private CommandType cmd;

    private  String cmdHandler;

    public CommandConfiguration(){}

    public CommandConfiguration(CommandType cmd, String cmdHandler) {
        this.cmd = cmd;
        this.cmdHandler = cmdHandler;
    }

    public CommandType getCmd() {
        return cmd;
    }

    public void setCmd(CommandType cmd) {
        this.cmd = cmd;
    }

    public String getCmdHandler() {
        return cmdHandler;
    }

    public void setCmdHandler(String cmdHandler) {
        this.cmdHandler = cmdHandler;
    }

}
