package cn.starboot.tim.client.command;

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


    /**
     * 通用cmd处理命令
     */
    private static final Map<Integer, ClientAbstractCmdHandler> handlerMap = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(CommandManager.class);

    private CommandManager(){};

    static{
        try {
            URL resource = CommandManager.class.getResource("command.properties");
            System.out.println(resource.toURI());
            System.out.println(resource.getPath());
            List<CommandConfiguration> configurations = CommandConfigurationFactory.parseConfiguration(new File(resource.getPath()));
            if (configurations == null) {
                System.out.println("使用路径获取配置文件");
                configurations = CommandConfigurationFactory
                        .parseConfiguration(new File("cn.starboot.tim.client.command.command.properties"));
                if (configurations == null) {
                    System.out.println("配置文件拿不到");
                }
            }
//            List<CommandConfiguration> configurations = new ArrayList<>();
//            // 登录响应
//            configurations.add(new CommandConfiguration(6, "org.tim.client.command.handler.LoginHandler"));
//            // 聊天请求
//            configurations.add(new CommandConfiguration(11, "org.tim.client.command.handler.ChatHandler"));
//            // 获取离线 漫游 历史 消息响应
//            configurations.add(new CommandConfiguration(20, "org.tim.client.command.handler.MessageDataHandler"));
//            // 获取在线用户响应
//            configurations.add(new CommandConfiguration(18, "org.tim.client.command.handler.OnlineUserHandler"));
//            // 鉴权响应
//            configurations.add(new CommandConfiguration(4, "org.tim.client.command.handler.AuthHandler"));
            init(configurations);
        } catch (Exception e) {
            log.error(e.toString(),e);
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
        int cmd_number = imCommandHandler.command().getCode();
        if(Objects.isNull(ReqCommandType.getCommandTypeByCode(cmd_number))) {
            throw new ImException("failed to register cmd handler, illegal cmd code:" + cmd_number + ",use Command.addAndGet () to add in the enumerated Command class!");
        }
        if(Objects.isNull(handlerMap.get(cmd_number)))
        {
            handlerMap.put(cmd_number, imCommandHandler);
        }else{
            throw new ImException("cmd code:"+cmd_number+",has been registered, please correct!");
        }
    }

    public static ClientAbstractCmdHandler removeCommand(ReqCommandType command){
        if(command == null) {
            return null;
        }
        int cmd_value = command.getCode();
        if(handlerMap.get(cmd_value) != null)
        {
            return handlerMap.remove(cmd_value);
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
        return handlerMap.get(command.getCode());
    }
}
