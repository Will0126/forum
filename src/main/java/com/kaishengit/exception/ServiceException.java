package com.kaishengit.exception;

/**
 * 自定义异常：用来标记业务异常
 */
public class ServiceException extends RuntimeException {

    public ServiceException(){}
    public ServiceException(Throwable th){
        super(th);
    }
    public ServiceException(String massage){
        super(massage);
    }
    public ServiceException(String massage,Throwable th) {
        super(massage,th);
    }

}
