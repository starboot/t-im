package cn.starboot.tim.client.command;

import cn.starboot.tim.client.ImClientChannelContext;
import cn.starboot.tim.client.ImClientConfig;
import cn.starboot.tim.client.command.handler.AbstractClientCmdHandler;
import cn.starboot.tim.client.intf.ClientTIMProcessor;
import cn.starboot.tim.common.command.AbstractCommandManager;
import cn.starboot.tim.common.command.TIMCommandType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by DELL(mxd) on 2021/12/24 16:44
 */
public class TIMClientCommandManager extends AbstractCommandManager<AbstractClientCmdHandler, ImClientChannelContext, ImClientConfig, ClientTIMProcessor> {

	private static final Logger LOGGER = LoggerFactory.getLogger(TIMClientCommandManager.class);

	/**
	 * 待装配的配置文件名字
	 */
	private static final String DEFAULT_CLASSPATH_CONFIGURATION_FILE = "command.properties";

	private static TIMClientCommandManager timServerTIMCommandManager;

	/**
	 * 通用cmd处理命令与命令码的Map映射
	 */
    private final Map<TIMCommandType, AbstractClientCmdHandler> handlerMap = new HashMap<>();

	private TIMClientCommandManager() {
		init(TIMClientCommandManager.class.getResource(DEFAULT_CLASSPATH_CONFIGURATION_FILE));
	}

	public synchronized static TIMClientCommandManager getTIMServerCommandManagerInstance() {
		if (Objects.isNull(timServerTIMCommandManager)) {
			timServerTIMCommandManager = new TIMClientCommandManager();
		}
		return timServerTIMCommandManager;
	}

	@Override
	protected Map<TIMCommandType, AbstractClientCmdHandler> getTIMCommandHandler() {
		return this.handlerMap;
	}
}
