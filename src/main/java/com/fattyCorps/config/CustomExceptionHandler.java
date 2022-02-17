package com.fattyCorps.config;

import com.fattyCorps.exception.CustomBaseException;
import com.fattyCorps.obj.msg.SRet;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常捕获类
 */
@ControllerAdvice
@ResponseBody
public class CustomExceptionHandler {
    @ExceptionHandler(CustomBaseException.class)
    public SRet handleHttpMessageNotReadableException(CustomBaseException ex) {
        // 直接向客户端返回消息
        return SRet.error(ex.getCode(), ex.getMsg());
    }
}