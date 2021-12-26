package org.tim.server.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tim.common.exception.ImException;
import org.tim.common.packets.Command;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by DELL(mxd) on 2021/12/24 16:44
 */
public class CommandManager {


    /**
     * 通用cmd处理命令
     */
    private static final Map<Integer, AbstractCmdHandler> handlerMap = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(CommandManager.class);

    private CommandManager(){};

    static{
        try {
            List<CommandConfiguration> configurations = CommandConfigurationFactory.parseConfiguration();
            init(configurations);
        } catch (Exception e) {
            log.error(e.toString(),e);
        }
    }

    private static void init(List<CommandConfiguration> configurations) throws Exception{
        for(CommandConfiguration configuration : configurations){
            AbstractCmdHandler cmdHandler = (AbstractCmdHandler) (Class.forName(configuration.getCmdHandler())).newInstance();
            registerCommand(cmdHandler);
        }
    }

    public static void registerCommand(AbstractCmdHandler imCommandHandler) throws Exception{
        if(imCommandHandler == null || imCommandHandler.command() == null) {
            return;
        }
        int cmd_number = imCommandHandler.command().getNumber();
        if(Objects.isNull(Command.forNumber(cmd_number))) {
            throw new ImException("failed to register cmd handler, illegal cmd code:" + cmd_number + ",use Command.addAndGet () to add in the enumerated Command class!");
        }
        if(Objects.isNull(handlerMap.get(cmd_number)))
        {
            handlerMap.put(cmd_number, imCommandHandler);
        }else{
            throw new ImException("cmd code:"+cmd_number+",has been registered, please correct!");
        }
    }

    public static AbstractCmdHandler removeCommand(Command command){
        if(command == null) {
            return null;
        }
        int cmd_value = command.getNumber();
        if(handlerMap.get(cmd_value) != null)
        {
            return handlerMap.remove(cmd_value);
        }
        return null;
    }

    public static <T> T getCommand(Command command,Class<T> clazz){
        AbstractCmdHandler cmdHandler = getCommand(command);
        if(cmdHandler != null){
            return (T) cmdHandler;
        }
        return null;
    }

    public static AbstractCmdHandler getCommand(Command command){
        if(command == null) {
            return null;
        }
        return handlerMap.get(command.getNumber());
    }
}
