package cn.starboot.tim.server;

import cn.starboot.socket.utils.config.Configuration;
import cn.starboot.socket.utils.config.ConfigurationFactory;
import cn.starboot.tim.common.util.TIMLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

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

//		configurationList.stream().distinct().filter(configuration -> configuration.getName().contains("tim.kernel")).forEach(configuration -> System.out.println(configuration.getName()));
		Map<String, String> kernelMap = new HashMap<>(20);
		configurationList.stream().distinct()
				.filter(configuration -> configuration.getName().contains("tim.kernel"))
				.forEach(configuration -> kernelMap.put(configuration.getName().split("tim.kernel.")[1], configuration.getValue()[0]));
		mapToObject(imServerConfig.getAioConfig(), kernelMap);

		System.out.println(imServerConfig.getAioConfig().getReadBufferSize());

		configurationList.stream().distinct()
				.filter(configuration -> configuration.getName().contains("tim.kernel")).forEach(new Consumer<Configuration>() {
			@Override
			public void accept(Configuration configuration) {
				System.out.println(configuration.getName().split("tim.kernel.")[1]);
			}
		});

//		initTIM(configurationList.stream().distinct().filter(configuration -> configuration.getName().contains("tim.server")));
//		initRedis(configurationList.stream().distinct().filter(configuration -> configuration.getName().contains("tim.redis")));
	}

	private static Map<String, String> initKernel(Stream<Configuration> kernelConfigurationStream) {
		Map<String, String> kernelMap = new HashMap<>(new BigDecimal(kernelConfigurationStream.count()).intValue());
		kernelConfigurationStream.forEach(configuration -> kernelMap.put(configuration.getName(), configuration.getValue()[0]));
		return kernelMap;
	}

	private static void initTIM(Stream<Configuration> timConfigurationStream) {
		Map<String, String> timMap = new HashMap<>(new BigDecimal(timConfigurationStream.count()).intValue());
		timConfigurationStream.forEach(configuration -> timMap.put(configuration.getName().split("tim.kernel.")[0], configuration.getValue()[0]));
	}

	private static void initRedis(Stream<Configuration> redisConfigurationStream) {
		Map<String, String> redisMap = new HashMap<>(new BigDecimal(redisConfigurationStream.count()).intValue());
		redisConfigurationStream.forEach(configuration -> redisMap.put(configuration.getName(), configuration.getValue()[0]));
	}

	private static void mapToObject(Object object, Map<String, String> map) throws InvocationTargetException, IllegalAccessException {
		System.out.println(map.size());
		map.keySet().forEach(System.out::println);
		for (Method method : object.getClass().getMethods()) {
			String name = method.getName();
			if (name.startsWith("set")) {
				String temp = name.substring(3);
				String key = temp.substring(0,1).toLowerCase() + temp.substring(1);
				System.out.println(key);
				String value = map.get(key);
				if (value != null) {
					System.out.println(key + "-" + value);
					method.invoke(object, Integer.valueOf(value));
					break;
				}

			}
		}
	}

}
