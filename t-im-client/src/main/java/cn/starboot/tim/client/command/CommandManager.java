package cn.starboot.tim.client.command;

import cn.starboot.tim.client.command.handler.ClientAbstractCmdHandler;
import cn.starboot.tim.common.command.CommandConfiguration;
import cn.starboot.tim.common.command.CommandConfigurationFactory;
import cn.starboot.tim.common.exception.ImException;
import cn.starboot.tim.common.command.ReqCommandType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.*;

/**
 * Created by DELL(mxd) on 2021/12/24 16:44
 */
public class CommandManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommandManager.class);

	/**
	 * 通用cmd处理命令与命令码的Map映射
	 */
    private static final Map<ReqCommandType, ClientAbstractCmdHandler> handlerMap = new HashMap<>();

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
            ClientAbstractCmdHandler cmdHandler = (ClientAbstractCmdHandler) (Class.forName(configuration.getCmdHandler())).newInstance();
            registerCommand(cmdHandler);
        }
    }

    public static void registerCommand(ClientAbstractCmdHandler imCommandHandler) throws Exception{
        if(imCommandHandler == null || imCommandHandler.command() == null) {
            return;
        }
		ReqCommandType command = imCommandHandler.command();
		if(Objects.isNull(ReqCommandType.getCommandTypeByCode(command.getCode()))) {
            throw new ImException("failed to register cmd handler, illegal cmd code:" + command.getCode() + ",use Command.addAndGet () to add in the enumerated Command class!");
        }
        if(Objects.isNull(handlerMap.get(command))) {
            handlerMap.put(command, imCommandHandler);
        }else {
            throw new ImException("cmd code:"+command.getCode()+",has been registered, please correct!");
        }
    }

    public static ClientAbstractCmdHandler removeCommand(ReqCommandType command){
        if(command == null) {
            return null;
        }
        if(handlerMap.get(command) != null) {
            return handlerMap.remove(command);
        }
        return null;
    }

    public static <T> T getCommand(ReqCommandType command, Class<T> clazz){
        ClientAbstractCmdHandler cmdHandler = getCommand(command);
        if(cmdHandler != null){
            return (T) cmdHandler;
        }
        return null;
    }

    public static ClientAbstractCmdHandler getCommand(ReqCommandType command){
        if(command == null) {
            return null;
        }
        return handlerMap.get(command);
    }
}
