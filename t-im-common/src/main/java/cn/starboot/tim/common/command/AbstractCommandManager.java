package cn.starboot.tim.common.command;

import cn.starboot.socket.utils.config.Configuration;
import cn.starboot.socket.utils.config.ConfigurationFactory;
import cn.starboot.tim.common.ImChannelContext;
import cn.starboot.tim.common.command.handler.AbstractCmdHandler;
import cn.starboot.tim.common.exception.ImException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractCommandManager<V extends AbstractCmdHandler<? extends ImChannelContext>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCommandManager.class);

	protected abstract Map<TIMCommandType, V> getTIMCommandHandler();

	protected void init(URL url) {
		try {
			if (LOGGER.isInfoEnabled()) {
				LOGGER.info("Configuring command from path: {}", url.getPath());
			}
			List<Configuration> configurations = ConfigurationFactory.parseConfiguration(url);
			if (configurations.size() == 0) {
				configurations = ConfigurationFactory.parseConfiguration(new File(url.getPath()));
			}
			if (configurations.size() > 0) {
				init0(configurations);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void init0(List<Configuration> configurations) throws Exception {
		for (Configuration configuration : configurations) {
			V cmdHandler = (V) (Class.forName(configuration.getValue()[0])).newInstance();
			registerCommand(cmdHandler);
		}
	}

	public void registerCommand(V imCommandHandler) throws Exception {
		if (imCommandHandler == null || imCommandHandler.command() == null) {
			return;
		}
		TIMCommandType command = imCommandHandler.command();
		if (Objects.isNull(TIMCommandType.getCommandTypeByCode(command.getCode()))) {
			throw new ImException("failed to register cmd handler, illegal cmd code:" + command + ",use Command.addAndGet () to add in the enumerated Command class!");
		}
		if (Objects.isNull(getTIMCommandHandler().get(command))) {
			getTIMCommandHandler().put(command, imCommandHandler);
		} else {
			throw new ImException("cmd code:" + command + ",has been registered, please correct!");
		}
	}

	public V removeCommand(TIMCommandType command) {
		if (command == null) {
			return null;
		}
		if (getTIMCommandHandler().get(command) != null) {
			return getTIMCommandHandler().remove(command);
		}
		return null;
	}

	public V getCommand(TIMCommandType command) {
		if (command == null) {
			return null;
		}
		return getTIMCommandHandler().get(command);
	}
}
