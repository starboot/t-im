package cn.starboot.tim.client.command;

import cn.starboot.socket.utils.config.Configuration;
import cn.starboot.socket.utils.config.ConfigurationFactory;
import cn.starboot.tim.client.command.handler.AbstractClientCmdHandler;

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
	 * 待装配的配置文件名字
	 */
	private static final String DEFAULT_CLASSPATH_CONFIGURATION_FILE = "command.properties";

	/**
	 * 通用cmd处理命令与命令码的Map映射
	 */
    private static final Map<ReqCommandType, AbstractClientCmdHandler> handlerMap = new HashMap<>();

    private CommandManager(){};

	static{
		try {
			URL url = CommandManager.class.getResource(DEFAULT_CLASSPATH_CONFIGURATION_FILE);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Configuring command from path: {}", url.getPath());
			}
			List<Configuration> configurations = ConfigurationFactory.parseConfiguration(url);
			if (configurations.size() == 0) {
				configurations = ConfigurationFactory.parseConfiguration(new File(url.getPath()));
			}
			if (configurations.size() > 0) {
				init(configurations);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

    private static void init(List<Configuration> configurations) throws Exception{
        for(Configuration configuration : configurations){
            AbstractClientCmdHandler cmdHandler = (AbstractClientCmdHandler) (Class.forName(configuration.getPath())).newInstance();
            registerCommand(cmdHandler);
        }
    }

    public static void registerCommand(AbstractClientCmdHandler imCommandHandler) throws Exception{
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

    public static AbstractClientCmdHandler removeCommand(ReqCommandType command){
        if(command == null) {
            return null;
        }
        if(handlerMap.get(command) != null) {
            return handlerMap.remove(command);
        }
        return null;
    }

    public static <T> T getCommand(ReqCommandType command, Class<T> clazz){
        AbstractClientCmdHandler cmdHandler = getCommand(command);
        if(cmdHandler != null){
            return (T) cmdHandler;
        }
        return null;
    }

    public static AbstractClientCmdHandler getCommand(ReqCommandType command){
        if(command == null) {
            return null;
        }
        return handlerMap.get(command);
    }
}
