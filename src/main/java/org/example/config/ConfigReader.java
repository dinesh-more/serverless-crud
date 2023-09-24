package org.example.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private static final String CONFIG_FILE = "config.properties";
    private static final Properties properties = new Properties();

    public static String getProperty(String key) {
        String property = "";
        try {
            ClassLoader classLoader = ConfigReader.class.getClassLoader();
            String path = classLoader.getResource(CONFIG_FILE).getPath();
            properties.load(new FileInputStream(path));
            property = properties.getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return property;
    }

}
