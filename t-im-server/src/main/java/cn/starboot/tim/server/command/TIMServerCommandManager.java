package cn.starboot.tim.server.command;

import cn.hutool.core.util.ObjectUtil;
import cn.starboot.tim.common.command.AbstractCommandManager;
import cn.starboot.tim.common.command.TIMCommandType;
import cn.starboot.tim.server.command.handler.AbstractServerCmdHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by DELL(mxd) on 2021/12/24 16:44
 */
public class TIMServerCommandManager extends AbstractCommandManager<AbstractServerCmdHandler> {

	private static final Logger LOGGER = LoggerFactory.getLogger(TIMServerCommandManager.class);

	/**
	 * 待装配的配置文件名字
	 */
	private static final String DEFAULT_CLASSPATH_CONFIGURATION_FILE = "command.properties";

	private static TIMServerCommandManager timServerTIMCommandManager;

	/**
	 * 通用cmd处理命令与命令码的Map映射
	 */
	private final Map<TIMCommandType, AbstractServerCmdHandler> handlerMap = new HashMap<>();

	private TIMServerCommandManager() {
		init(TIMServerCommandManager.class.getResource(DEFAULT_CLASSPATH_CONFIGURATION_FILE));
	}

	public synchronized static TIMServerCommandManager getTIMServerCommandManagerInstance() {
		if (ObjectUtil.isEmpty(timServerTIMCommandManager)) {
			timServerTIMCommandManager = new TIMServerCommandManager();
		}
		return timServerTIMCommandManager;
	}

	@Override
	protected Map<TIMCommandType, AbstractServerCmdHandler> getTIMCommandHandler() {
		return this.handlerMap;
	}
}
