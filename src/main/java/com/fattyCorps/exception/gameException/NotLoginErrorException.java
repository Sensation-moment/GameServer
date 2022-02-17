package com.fattyCorps.exception.gameException;

import com.fattyCorps.exception.CustomBaseException;
import com.fattyCorps.obj.msg.HttpStatus;

/**
 * 未登录异常
 */
public class NotLoginErrorException extends CustomBaseException {
    public NotLoginErrorException() {
        super(HttpStatus.NOT_LOGIN_ERROR.getCode(), HttpStatus.NOT_LOGIN_ERROR.getMsg());
    }
}