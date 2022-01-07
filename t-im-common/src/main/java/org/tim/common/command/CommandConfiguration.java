package org.tim.common.command;

import java.util.Properties;

/**
 * Created by DELL(mxd) on 2021/12/24 16:44
 */
public class CommandConfiguration {
    private  int cmd ;
    private  String cmdHandler ;

    public CommandConfiguration(){}

    public CommandConfiguration(String cmd, Properties prop){
        this.cmd = Integer.parseInt(cmd);
        cmdHandler = prop.getProperty(cmd);
    }

    public CommandConfiguration(int cmd, String cmdHandler) {
        this.cmd = cmd;
        this.cmdHandler = cmdHandler;
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public String getCmdHandler() {
        return cmdHandler;
    }

    public void setCmdHandler(String cmdHandler) {
        this.cmdHandler = cmdHandler;
    }

}
