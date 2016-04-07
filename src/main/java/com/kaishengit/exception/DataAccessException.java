package com.kaishengit.exception;

/**
 * 自定义异常：用来标记数据库访问出现的异常
 */
public class DataAccessException extends RuntimeException {

    public DataAccessException(){}
    public DataAccessException(Throwable th){}
    public DataAccessException(Throwable th, String massage) {
        super(massage,th);
    }

}
