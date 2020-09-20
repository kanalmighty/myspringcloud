package com.zk.cloudalibaba.excepition;

import org.springframework.stereotype.Component;


public class ServiceException extends Exception {
    //用详细信息指定一个异常
    public ServiceException(String message){
        super(message);
    }

    //用指定的详细信息和原因构造一个新的异常
    public ServiceException(String message, Throwable cause){
        super(message,cause);
    }

    //用指定原因构造一个新的异常
    public ServiceException(Throwable cause) {
        super(cause);
    }
}
