import com.google.common.collect.Lists;
import net.spy.memcached.AddrUtil;
import net.spy.memcached.MemcachedClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class Text {

    public static void main(String[] args) throws IOException {

        //分布式缓存---客户端
        //List<String> address = Lists.newArrayList("127.0.0.1:11211","others address");
        MemcachedClient client = new MemcachedClient(AddrUtil.getAddresses("127.0.0.1:11211"));
        //存储
        client.add("name",20,"java");
        //获取
        String str = (String) client.get("name");
        //删除
        client.delete("name");





        /* 日志
        //创建
        //1、Logger logger = Logger.getLogger("完全限定名");
        //2、
        //Logger logger = Logger.getLogger(Text.class);//自动获取完全限定名

        Logger logger = LoggerFactory.getLogger(Text.class);

        //日志级别
        logger.debug("最低级别");
        logger.info("info 级别高于 debug");
        logger.warn("warm 级别高于 info");
        logger.error("error 级别高于 warm");
        //logger.fatal("致命级别"); slf4j没有这个级别
        */
    }
}
