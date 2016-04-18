package com.kaishengit.util;

import com.google.common.collect.Maps;

import java.util.Map;

public class SimpleCache {

    private static Map<String,Object> map = Maps.newHashMap();

    public static void add(String key, Object value) {
        map.put(key, value);
    }

    public static Object get(String key) {
        return map.get(key);
    }


}
