package com.fattyCorps.exception;

import lombok.Data;

/**
 * 定义基础异常类
 */
@Data
public class CustomBaseException extends RuntimeException{
    protected Integer code;
    protected String msg;

    public CustomBaseException(Integer code, String msg){
        this.msg = msg;
        this.code = code;
    }
}
