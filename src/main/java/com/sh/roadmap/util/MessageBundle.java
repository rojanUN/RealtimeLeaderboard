package com.sh.roadmap.util;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
@Slf4j
public class MessageBundle {

    public static final Map<String, String> lookup = new HashMap<>();
    private static final String ERROR_FILE = "errors";
    private static final String MESSAGE_FILE = "messages";
    private static final String PROPERTIES = "properties";
    private static final String DOT = ".";

    @PostConstruct
    public void initializeError() {
        try {
            Properties prop = getProperties(ERROR_FILE + DOT + PROPERTIES);
            Properties properties = getProperties(MESSAGE_FILE + DOT + PROPERTIES);
            for (String key : prop.stringPropertyNames()) {
                String value = prop.getProperty(key);
                lookup.put(key, value);
            }
            for (String key : properties.stringPropertyNames()) {
                String value = properties.getProperty(key);
                lookup.put(key, value);
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    public static Properties getProperties(String filename) throws IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(filename);
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties;
    }

    public static String getMessageByCode(String key) {
        return lookup.getOrDefault(key, Constant.DEFAULT_SUCCESS_MESSAGE);
    }

    public static String getErrorMessageByCode(String key) {
        return lookup.getOrDefault(key, Constant.DEFAULT_ERROR_MESSAGE);
    }


}
