package cn.starboot.tim.server;

import cn.starboot.socket.utils.config.Configuration;
import cn.starboot.socket.utils.config.ConfigurationFactory;
import cn.starboot.tim.common.util.TIMLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TIMConfigManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(TIMConfigManager.class);

	/**
	 * 待装配的配置文件名字
	 */
	private static final String DEFAULT_CLASSPATH_CONFIGURATION_FILE = "tim.properties";

	TIMConfigManager() { }

	static void init(ImServerConfig imServerConfig) {
		try {
			URL url = ClassLoader.getSystemResource(DEFAULT_CLASSPATH_CONFIGURATION_FILE);
			List<Configuration> configurations = ConfigurationFactory.parseConfiguration(url);
			if (configurations.size() == 0)
				configurations = ConfigurationFactory.parseConfiguration(new File(url.getPath()));

			if (configurations.size() > 0) {
				TIMLogUtil.info(LOGGER, "load tim.properties success from {}", url.getPath());
				init0(configurations, imServerConfig);
			} else
				TIMLogUtil.error(LOGGER, "load tim.properties failed, please ensure the tim.properties in resources file");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void init0(List<Configuration> configurationList, ImServerConfig imServerConfig) throws InvocationTargetException, IllegalAccessException {
		Map<String, String> kernelMap = new HashMap<>(configurationList.size());

		// 初始化内核配置
		configurationList.stream().distinct()
				.filter(configuration -> configuration.getName().contains("tim.kernel"))
				.forEach(configuration -> kernelMap.put(configuration.getName().split("tim.kernel.")[1], configuration.getValue()[0]));
		mapToObject(imServerConfig.getAioConfig(), kernelMap);
		TIMLogUtil.info(LOGGER, "kernel Configuration : {}", imServerConfig.getAioConfig());
		kernelMap.clear();

		// 初始化Redis配置
		configurationList.stream().distinct()
				.filter(configuration -> configuration.getName().contains("tim.redis"))
				.forEach(configuration -> kernelMap.put(configuration.getName().split("tim.redis.")[1], configuration.getValue()[0]));
		mapToObject(imServerConfig.getRedisConfig(), kernelMap);
		TIMLogUtil.info(LOGGER, "Redis Configuration : {}", imServerConfig.getRedisConfig());
		kernelMap.clear();

		// 初始化TIM Server配置
		configurationList.stream().distinct()
				.filter(configuration -> configuration.getName().contains("tim.server"))
				.forEach(configuration -> kernelMap.put(configuration.getName().split("tim.server.")[1], configuration.getValue()[0]));
		mapToObject(imServerConfig, kernelMap);
		TIMLogUtil.info(LOGGER, "TIM Server Configuration : {}", imServerConfig);
		kernelMap.clear();
	}

	private static void mapToObject(Object object, Map<String, String> map) throws InvocationTargetException, IllegalAccessException {
		for (Method method : object.getClass().getMethods()) {
			String name = method.getName();
			if (name.startsWith("set")) {
				String temp = name.substring(3);
				String key = temp.substring(0,1).toLowerCase() + temp.substring(1);
				String value = map.get(key);
				if (value != null) {
					Class<?>[] parameterTypes = method.getParameterTypes();
					switch (parameterTypes[0].getName()) {
						case "int":
						case "java.lang.Integer":
							if (value.contains("*")) {
								int result = 1;
								for (String s:
								value.split(" * ")) {
									if (s.equals("*"))
										continue;
									result = result * Integer.parseInt(s);
								}
								method.invoke(object, result);
							} else if (value.contains("-")) {
								int result = 0;
								for (String s:
										value.split(" - ")) {
									if (s.equals("-"))
										continue;
									result = result - Integer.parseInt(s);
								}
								method.invoke(object, result);
							} else if (value.contains("+")) {
								int result = 0;
								for (String s:
										value.split(" + ")) {
									if (s.equals("+"))
										continue;
									result = result + Integer.parseInt(s);
								}
								method.invoke(object, result);
							} else if (value.contains("/")) {
								throw new UnsupportedOperationException("tim.properties int various unsupported use '/' operations");
							} else
								method.invoke(object, Integer.parseInt(value));
							break;
						case "boolean":
						case "java.lang.Boolean":
							method.invoke(object, Boolean.getBoolean(value));
							break;
						case "java.lang.String":
							method.invoke(object, value);
							break;
						default:
							break;
					}
				}
			}
		}
	}

}
