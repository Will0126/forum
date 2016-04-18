package com.kaishengit.util;


import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

public class EnCacheUtil {

    private static CacheManager cacheManager = new CacheManager();
    //存
    public static void set(String simpleCache,String key,Object value) {
        Ehcache ehcache = cacheManager.getEhcache(simpleCache);
        Element element = new Element(key,value);
        ehcache.put(element);
    }

    //取
   public static Object get(String simpleCache, String key){
       Ehcache ehcache = cacheManager.getEhcache(simpleCache);
       Element element = ehcache.get(key);
       if(element == null){
           return null;
       } else {
           return element.getObjectValue();
       }
   }

    //删除
    public static void delete(String simpleCache, String key){
        Ehcache ehcache = cacheManager.getEhcache(simpleCache);
        ehcache.remove(key);
    }
}
