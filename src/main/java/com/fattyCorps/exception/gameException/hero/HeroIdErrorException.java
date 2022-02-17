package com.fattyCorps.exception.gameException.hero;

import com.fattyCorps.exception.CustomBaseException;
import com.fattyCorps.obj.msg.HttpStatus;

/**
 * 英雄ID错误异常
 */
public class HeroIdErrorException extends CustomBaseException {
    public HeroIdErrorException() {
        super(HttpStatus.HERO_ID_ERROR.getCode(), HttpStatus.HERO_ID_ERROR.getMsg());
    }
}
