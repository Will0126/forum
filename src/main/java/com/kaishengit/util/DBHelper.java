package com.kaishengit.util;

import com.kaishengit.exception.DataAccessException;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

import java.sql.SQLException;

/**
 * 数据库查询
 */
public class DBHelper {

    /**
     * 执行insert增加 delete删除 update修改 语句
     */
    public static void update(String sql,Object... params){
        QueryRunner queryRunner = new QueryRunner(ConnectionManager.getDataSource());
        try {
            queryRunner.update(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException(e,"SQL Update Exception" + sql);
        }
    }

    /**
     * 执行select查询语句
     */
   public static <T> T query(String sql, ResultSetHandler<T> handler, Object... params) {
       QueryRunner queryRunner = new QueryRunner(ConnectionManager.getDataSource());
       try {
           return queryRunner.query(sql,handler,params);
       } catch (SQLException e) {
           e.printStackTrace();
           throw new DataAccessException(e,"SQL query Exception" + sql);
       }
   }

}
