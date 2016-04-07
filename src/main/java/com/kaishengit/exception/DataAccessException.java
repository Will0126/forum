package com.kaishengit.exception;

/**
 * 自定义异常：用来标记数据库访问出现的异常
 */
public class DataAccessException extends RuntimeException {

    public DataAccessException(){}
    public DataAccessException(Throwable th){ super(th);}
    public DataAccessException(String massage){ super(massage);}
    public DataAccessException(Throwable th, String massage) {
        super(massage,th);
    }

}
