package cn.starboot.tim.common.util;

import org.slf4j.Logger;

public class TIMLogUtil {


	/**
	 * info 日志
	 *
	 * @param LOGGER 日志类
	 * @param key 键值
	 * @param content 参数内容
	 */
	public static void info(Logger LOGGER, String key , Object ...content) {
		if (LOGGER.isInfoEnabled()) {
			LOGGER.info(key, content);
		}
	}

	/**
	 * debug 日志
	 *
	 * @param LOGGER 日志类
	 * @param key 键值
	 * @param content 参数内容
	 */
	public static void debug(Logger LOGGER, String key , Object ...content) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(key, content);
		}
	}

	/**
	 * warn 日志
	 *
	 * @param LOGGER 日志类
	 * @param key 键值
	 * @param content 参数内容
	 */
	public static void warn(Logger LOGGER, String key , Object ...content) {
		if (LOGGER.isWarnEnabled()) {
			LOGGER.warn(key, content);
		}
	}

	/**
	 * error 日志
	 *
	 * @param LOGGER 日志类
	 * @param key 键值
	 * @param content 参数内容
	 */
	public static void error(Logger LOGGER, String key , Object ...content) {
		if (LOGGER.isErrorEnabled()) {
			LOGGER.error(key, content);
		}
	}
}
