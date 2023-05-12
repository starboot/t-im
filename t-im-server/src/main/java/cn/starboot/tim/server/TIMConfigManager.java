package cn.starboot.tim.server;

import cn.starboot.socket.utils.config.Configuration;
import cn.starboot.socket.utils.config.ConfigurationFactory;
import cn.starboot.tim.server.command.TIMServerCommandManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TIMConfigManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(TIMConfigManager.class);

	/**
	 * 通用cmd处理命令与命令码的Map映射
	 */
	private final Map<String, String> handlerMap = new HashMap<>();
	/**
	 * 待装配的配置文件名字
	 */
	private static final String DEFAULT_CLASSPATH_CONFIGURATION_FILE = "tim.properties";

	static {
		try {
			URL url = TIMServerCommandManager.class.getResource(DEFAULT_CLASSPATH_CONFIGURATION_FILE);
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Configuring command from path: {}", url.getPath());
			}
			List<Configuration> configurations = ConfigurationFactory.parseConfiguration(url);
			if (configurations.size() == 0) {
				configurations = ConfigurationFactory.parseConfiguration(new File(url.getPath()));
			}
			if (configurations.size() > 0) {
//				init0(configurations);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
