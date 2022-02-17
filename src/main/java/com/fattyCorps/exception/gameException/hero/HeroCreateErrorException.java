package com.fattyCorps.exception.gameException.hero;

import com.fattyCorps.exception.CustomBaseException;
import com.fattyCorps.obj.msg.HttpStatus;


/**
 * 英雄创建错误异常
 */
public class HeroCreateErrorException extends CustomBaseException {
    public HeroCreateErrorException() {
        super(HttpStatus.HERO_CREATE_ERROR.getCode(), HttpStatus.HERO_CREATE_ERROR.getMsg());
    }
}
