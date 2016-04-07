package com.kaishengit.util;

import java.io.IOException;
import java.util.Properties;

/**
 * 读取config.properties文件的工具类
 * @author Will
 */
public class ConfigProp {

    private static Properties prop = new Properties();

    /**
     * 读取config.properties文件
     */
    static{
        try {
            prop.load(ConfigProp.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("读取config.properties文件异常");
        }
    }

    /**
     * 根据键读取config.properties相对应的值
     * @param key 键
     * @return key 键对应的值
     */
    public static String get(String key) {
        return prop.getProperty(key);
    }
}
