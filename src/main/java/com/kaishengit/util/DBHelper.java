package com.kaishengit.util;

import com.kaishengit.exception.DataAccessException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

/**
 * 数据库查询
 */
public class DBHelper {
    //创建API层
    private static Logger logger = LoggerFactory.getLogger(DBHelper.class);

    /**
     * 执行insert增加 delete删除 update修改 语句
     */
    public static void update(String sql,Object... params) {
        QueryRunner queryRunner = new QueryRunner(ConnectionManager.getDataSource());
        try {
            queryRunner.update(sql, params);
            logger.debug("SQL:{}",sql);
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("执行{}异常",sql);
            throw new DataAccessException(e,"SQL Update Exception" + sql);
        }
    }

    /**
     * insert新增，并且返回主键
     */
    public static Long insert(String sql, Object... params){
        QueryRunner queryRunner = new QueryRunner(ConnectionManager.getDataSource());
        try {
            Long result =  queryRunner.insert(sql,new ScalarHandler<Long>(),params);
            logger.debug("SQL:{}",sql);
            return result;
            //ScalarHandler 将一行一列的结果返回
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("执行{}异常",sql);
            throw new DataAccessException(e,"执行新增出错" + sql );
        }
    }




    /**
     * 执行select查询语句
     */
   public static <T> T query(String sql, ResultSetHandler<T> handler, Object... params) {
       QueryRunner queryRunner = new QueryRunner(ConnectionManager.getDataSource());
       try {
           T t = queryRunner.query(sql,handler,params);
           logger.debug("SQL:{}",sql);
           return t;
       } catch (SQLException e) {
           e.printStackTrace();
           logger.error("执行{}异常",sql);
           throw new DataAccessException(e,"SQL query Exception" + sql);
       }
   }

}
