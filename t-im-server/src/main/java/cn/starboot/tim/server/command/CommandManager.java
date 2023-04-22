package cn.starboot.tim.server.command;

import cn.hutool.core.util.ObjectUtil;
import cn.starboot.tim.common.command.CommandConfiguration;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.packet.CommandType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by DELL(mxd) on 2021/12/24 16:44
 */
public class CommandManager {


    /**
     * 通用cmd处理命令
     */
    private static final Map<CommandType, ServerAbstractCmdHandler> handlerMap = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(CommandManager.class);

    private CommandManager(){};

    static{
        try {
            List<CommandConfiguration> configurations = new ArrayList<>();
            for (Command c : Command.values()
                 ) {
                configurations.add(new CommandConfiguration(c.getCmd(), c.getPath()));
            }
            init(configurations);
        } catch (Exception e) {
            log.error(e.toString(),e);
        }
    }

    private static void init(List<CommandConfiguration> configurations) throws Exception{
        for(CommandConfiguration configuration : configurations){
            ServerAbstractCmdHandler cmdHandler = (ServerAbstractCmdHandler) (Class.forName(configuration.getCmdHandler())).newInstance();
            registerCommand(cmdHandler);
        }
    }

    public static void registerCommand(ServerAbstractCmdHandler imCommandHandler) throws Exception{
        if(imCommandHandler == null || imCommandHandler.command() == null) {
            return;
        }
        CommandType command = imCommandHandler.command();
        if(ObjectUtil.isNull(Command.forNumber(command))) {
            throw new ImException("failed to register cmd handler, illegal cmd code:" + command + ",use Command.addAndGet () to add in the enumerated Command class!");
        }
        if(ObjectUtil.isNull(handlerMap.get(command)))
        {
            handlerMap.put(command, imCommandHandler);
        }else{
            throw new ImException("cmd code:"+command+",has been registered, please correct!");
        }
    }

    public static ServerAbstractCmdHandler removeCommand(CommandType command){
        if(command == null) {
            return null;
        }
        if(handlerMap.get(command) != null)
        {
            return handlerMap.remove(command);
        }
        return null;
    }

    public static <T> T getCommand(CommandType command, Class<T> clazz){
        ServerAbstractCmdHandler cmdHandler = getCommand(command);
        if(cmdHandler != null){
            return (T) cmdHandler;
        }
        return null;
    }

    public static ServerAbstractCmdHandler getCommand(CommandType command){
        if(command == null) {
            return null;
        }
        return handlerMap.get(command);
    }
}
