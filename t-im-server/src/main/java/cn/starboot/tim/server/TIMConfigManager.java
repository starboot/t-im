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

/**
 * TIM 配置类映射Java对象
 *
 * @author MDong
 */
class TIMConfigManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(TIMConfigManager.class);

	/**
	 * 待装配的配置文件名字
	 */
	private static final String DEFAULT_CLASSPATH_CONFIGURATION_FILE = "tim.properties";

	private static String host;

	private static int port;

	private static final String project = "tim";

	private static final String point = ".";

	private final ImServerConfig imServerConfig;

	private static List<Configuration> configurations;

	private static Map<String, String> configurationMap;

	static {
		try {
			URL url = ClassLoader.getSystemResource(DEFAULT_CLASSPATH_CONFIGURATION_FILE);
			configurations = ConfigurationFactory.parseConfiguration(url);
			if (configurations.size() == 0)
				configurations = ConfigurationFactory.parseConfiguration(new File(url.getPath()));
			if (configurations.size() > 0) {
				TIMLogUtil.info(LOGGER, "load tim.properties success from {}", url.getPath());
				configurationMap = new HashMap<>(configurations.size());
				String inherentHost = project + point + ConfigurationTypeEnum.SERVER.getType() + point + "host";
				String inherentPort = project + point + ConfigurationTypeEnum.SERVER.getType() + point + "port";
				configurations
						.stream()
						.filter(configuration ->
								configuration
										.getName()
										.equals(inherentHost)
								|| configuration
										.getName()
										.equals(inherentPort))
						.forEach(configuration -> {
									if (configuration
											.getName()
											.equals(inherentHost)) {
										host = configuration.getValue()[0];
									}else
										port = Integer.parseInt(configuration.getValue()[0]);
								}
						);
			} else
				TIMLogUtil.error(LOGGER, "load tim.properties failed, please ensure the tim.properties in resources file");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private TIMConfigManager(ImServerConfig imServerConfig) {
		this.imServerConfig = imServerConfig;
	}

	public static class Builder {

		private static TIMConfigManager timConfigManager = null;

		public static synchronized TIMConfigManager build(ImServerConfig imServerConfig) {
			if (timConfigManager == null) {
				timConfigManager = new TIMConfigManager(imServerConfig);
			}
			return timConfigManager;
		}
	}

	static String getHost() {
		return host;
	}

	static int getPort() {
		return port;
	}

	TIMConfigManager initTIMServerConfiguration() throws InvocationTargetException, IllegalAccessException {
		// 初始化TIM Server配置
		init0(configurations, this.imServerConfig, ConfigurationTypeEnum.SERVER);
		return this;
	}

	TIMConfigManager initRedisConfiguration() throws InvocationTargetException, IllegalAccessException {
		// 初始化Redis配置
		init0(configurations, this.imServerConfig, ConfigurationTypeEnum.REDIS);
		return this;
	}

	TIMConfigManager initKernelConfiguration() throws InvocationTargetException, IllegalAccessException {
		// 初始化内核配置
		init0(configurations, this.imServerConfig, ConfigurationTypeEnum.KERNEL);
		return this;
	}

	private void init0(List<Configuration> configurationList, ImServerConfig imServerConfig, ConfigurationTypeEnum configurationTypeEnum) throws InvocationTargetException, IllegalAccessException {
		String contains = project + point + configurationTypeEnum.getType();
		String split = contains + point;
		configurationList.stream().distinct()
				.filter(configuration -> configuration.getName().contains(contains))
				.forEach(configuration -> configurationMap.put(configuration.getName().split(split)[1], configuration.getValue()[0]));
		Object object = null;
		switch (configurationTypeEnum) {
			case SERVER: {
				object = imServerConfig;
				break;
			}
			case KERNEL:
				object = imServerConfig.getAioConfig();
				break;
			case REDIS:
				object = imServerConfig.getRedisConfig();
				break;
		}
		mapToObject(object, configurationMap);
		if (imServerConfig.isOut())
			TIMLogUtil.info(LOGGER, configurationTypeEnum.getMsg(), object);
		configurationMap.clear();
	}

	private enum ConfigurationTypeEnum {

		SERVER("server", "TIM Server Configuration : {}"),

		KERNEL("kernel", "Kernel Configuration : {}"),

		REDIS("redis", "Redis Configuration : {}");

		private final String type;

		private final String msg;

		ConfigurationTypeEnum(String type, String msg) {
			this.type = type;
			this.msg = msg;
		}

		private String getType() {
			return type;
		}

		private String getMsg() {
			return msg;
		}
	}

	private void mapToObject(Object object, Map<String, String> map) throws InvocationTargetException, IllegalAccessException {
		for (Method method : object.getClass().getMethods()) {
			String name = method.getName();
			if (name.startsWith("set")) {
				String temp = name.substring(3);
				String key = temp.substring(0, 1).toLowerCase() + temp.substring(1);
				String value = map.get(key);
				if (value != null) {
					Class<?>[] parameterTypes = method.getParameterTypes();
					switch (parameterTypes[0].getName()) {
						case "int":
						case "java.lang.Integer":
							if (value.contains("*")) {
								int result = 1;
								for (String s :
										value.split(" * ")) {
									if (s.equals("*"))
										continue;
									result = result * Integer.parseInt(s);
								}
								method.invoke(object, result);
							} else if (value.contains("-")) {
								int result = 0;
								for (String s :
										value.split(" - ")) {
									if (s.equals("-"))
										continue;
									result = result - Integer.parseInt(s);
								}
								method.invoke(object, result);
							} else if (value.contains("+")) {
								int result = 0;
								for (String s :
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
							method.invoke(object, Boolean.parseBoolean(value));
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
