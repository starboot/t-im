package org.tim.common.command;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by DELL(mxd) on 2021/12/24 16:43
 */
public class CommandConfigurationFactory {

    private static final Logger log = LoggerFactory.getLogger(CommandConfigurationFactory.class.getName());

    private CommandConfigurationFactory() {
    }

    /**
     * Configures a bean from an property file.
     */
    public static List<CommandConfiguration> parseConfiguration(final File file) throws Exception {
        if (file == null) {
            throw new Exception("Attempt to configure command from null file.");
        }
        log.info("Configuring command from file: {}", file);
        List<CommandConfiguration> configurations;
        InputStream input = null;
        try {
            input = new BufferedInputStream(new FileInputStream(file));
            configurations = parseConfiguration(input);
        } catch (Exception e) {
            throw new Exception("Error configuring from " + file + ". Initial cause was " + e.getMessage(), e);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                log.error("IOException while closing configuration input stream. Error was " + e.getMessage());
            }
        }
        return configurations;
    }
    /**
     * Configures a bean from an property file available as an URL.
     */
    public static List<CommandConfiguration> parseConfiguration(final URL url) throws Exception {
        log.info("Configuring command from URL: {}", url);
        List<CommandConfiguration> configurations;
        InputStream input = null;
        try {
            input = url.openStream();
            configurations = parseConfiguration(input);
        } catch (Exception e) {
            throw new Exception("Error configuring from " + url + ". Initial cause was " + e.getMessage(), e);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                log.error("IOException while closing configuration input stream. Error was " + e.getMessage());
            }
        }
        return configurations;
    }
    /**
     * Configures a bean from an property file in the classpath.
     */
    public static List<CommandConfiguration> parseConfiguration(String filePath) throws Exception {
        ClassLoader standardClassloader = Thread.currentThread().getContextClassLoader();
        URL url = null;
        if (standardClassloader != null) {
            log.info("by standardClassloader:{}",standardClassloader.getResource(filePath));
            url = standardClassloader.getResource(filePath);
        }
        if (url == null) {
            log.info("by CommandConfigurationFactory");
            url = CommandConfigurationFactory.class.getResource(filePath);
        }
        if (url == null) {
            FileReader fileReader = FileReader.create(new File(filePath));
            if (ObjectUtil.isNotEmpty(fileReader)) {
                BufferedInputStream inputStream = fileReader.getInputStream();
                parseConfiguration(inputStream);
            }
        }
        if (url != null) {
            log.debug("Configuring command from command.properties found in the classpath: " + url);
        } else {
            log.warn("No configuration found. Configuring command from command.properties "
                    + " found in the classpath: {}", filePath);
        }
        return parseConfiguration(url);
    }

    /**
     * Configures a bean from an property input stream.
     */
    public static List<CommandConfiguration> parseConfiguration(final InputStream inputStream) throws Exception {

        log.debug("Configuring command from InputStream");

        List<CommandConfiguration> configurations = new ArrayList<>();
        try {
            Properties props = new Properties();
            props.load(inputStream);
            for(String key : props.stringPropertyNames()){
                configurations.add(new CommandConfiguration(key , props));
            }
        } catch (Exception e) {
            throw new Exception("Error configuring from input stream. Initial cause was " + e.getMessage(), e);
        }
        return configurations;
    }
}
