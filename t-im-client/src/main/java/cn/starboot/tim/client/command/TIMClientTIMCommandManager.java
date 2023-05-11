package cn.starboot.tim.client.command;

import cn.starboot.tim.client.command.handler.AbstractClientCmdHandler;
import cn.starboot.tim.common.command.AbstractTIMCommandManager;
import cn.starboot.tim.common.command.TIMCommandType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by DELL(mxd) on 2021/12/24 16:44
 */
public class TIMClientTIMCommandManager extends AbstractTIMCommandManager<AbstractClientCmdHandler> {

	private static final Logger LOGGER = LoggerFactory.getLogger(TIMClientTIMCommandManager.class);

	/**
	 * 待装配的配置文件名字
	 */
	private static final String DEFAULT_CLASSPATH_CONFIGURATION_FILE = "command.properties";

	private static TIMClientTIMCommandManager timServerTIMCommandManager;

	/**
	 * 通用cmd处理命令与命令码的Map映射
	 */
    private final Map<TIMCommandType, AbstractClientCmdHandler> handlerMap = new HashMap<>();

	private TIMClientTIMCommandManager() {
		init(TIMClientTIMCommandManager.class.getResource(DEFAULT_CLASSPATH_CONFIGURATION_FILE));
	}

	public synchronized static TIMClientTIMCommandManager getTIMServerCommandManagerInstance() {
		if (Objects.isNull(timServerTIMCommandManager)) {
			timServerTIMCommandManager = new TIMClientTIMCommandManager();
		}
		return timServerTIMCommandManager;
	}

	@Override
	protected Map<TIMCommandType, AbstractClientCmdHandler> getTIMCommandHandler() {
		return this.handlerMap;
	}
}
