package cn.starboot.tim.server.command;

import cn.hutool.core.util.ObjectUtil;
import cn.starboot.tim.common.command.CommandConfiguration;
import cn.starboot.tim.common.command.CommandConfigurationFactory;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.command.ReqCommandType;
import cn.starboot.tim.server.command.handler.ServerAbstractCmdHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL(mxd) on 2021/12/24 16:44
 */
public class CommandManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommandManager.class);

    /**
     * 通用cmd处理命令与命令码的Map映射
     */
    private static final Map<ReqCommandType, ServerAbstractCmdHandler> handlerMap = new HashMap<>();

    private CommandManager(){};

    static{
        try {
            URL url = CommandManager.class.getResource("command.properties");
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Configuring command from path: {}", url.getPath());
			}
            List<CommandConfiguration> configurations = CommandConfigurationFactory.parseConfiguration(url);
            if (configurations == null) {
                configurations = CommandConfigurationFactory.parseConfiguration(new File(url.getPath()));
                if (configurations == null && LOGGER.isErrorEnabled()) {
					LOGGER.error("Configuring command get failed");
                }
            }
            if (configurations != null) {
				init(configurations);
			}
        } catch (Exception e) {
			LOGGER.error(e.toString());
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
        ReqCommandType command = imCommandHandler.command();
        if(ObjectUtil.isNull(ReqCommandType.getCommandTypeByCode(command.getCode()))) {
            throw new ImException("failed to register cmd handler, illegal cmd code:" + command + ",use Command.addAndGet () to add in the enumerated Command class!");
        }
        if(ObjectUtil.isNull(handlerMap.get(command))) {
            handlerMap.put(command, imCommandHandler);
        }else {
            throw new ImException("cmd code:"+command+",has been registered, please correct!");
        }
    }

    public static ServerAbstractCmdHandler removeCommand(ReqCommandType command){
        if(command == null) {
            return null;
        }
        if(handlerMap.get(command) != null) {
            return handlerMap.remove(command);
        }
        return null;
    }

    public static <T> T getCommand(ReqCommandType command, Class<T> clazz){
        ServerAbstractCmdHandler cmdHandler = getCommand(command);
        if(cmdHandler != null){
            return (T) cmdHandler;
        }
        return null;
    }

    public static ServerAbstractCmdHandler getCommand(ReqCommandType command){
        if(command == null) {
            return null;
        }
        return handlerMap.get(command);
    }
}
