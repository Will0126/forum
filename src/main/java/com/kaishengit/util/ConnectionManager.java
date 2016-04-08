package com.kaishengit.util;

import com.kaishengit.exception.DataAccessException;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 连接数据库
 * 建立数据库连接池
 */
public class ConnectionManager {

    private static BasicDataSource basicDataSource = new BasicDataSource();
    //连接池只需要建立一次，为了保证只连接池只建立一次，所以将代码写在static{}中
    static {
        //driver、url、username、password数据库连接参数设置
        basicDataSource.setDriverClassName(ConfigProp.get("jdbc.driver"));
        basicDataSource.setUrl(ConfigProp.get("jdbc.url"));
        basicDataSource.setUsername(ConfigProp.get("jdbc.username"));
        basicDataSource.setPassword(ConfigProp.get("jdbc.password"));

        //连接池参数设置
        basicDataSource.setInitialSize(5);//初始化时数据库连接池数量
        basicDataSource.setMinIdle(5);//最少数量
        basicDataSource.setMaxIdle(20);//最多数量
        basicDataSource.setMaxWaitMillis(5000);//最大等待时间

    }


    public static Connection getConnection(){
        try {
            return basicDataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException(e,"数据库连接异常");
        }
    }

    /**
     *用于优化DBHelper的QueryRunner，传入QueryRunner(DataSource dataSource)
     *再调用该类的方法时只需要传入sql和参数，会自动连接连接池
     *而BasicDataSoure实现了DataSource接口
     */
    public static DataSource getDataSource(){
        return basicDataSource;
    }



}
