package com.fattyCorps.exception.gameException.hero;

import com.fattyCorps.exception.CustomBaseException;
import com.fattyCorps.obj.msg.HttpStatus;

/**
 * 英雄最大升星错误异常
 */
public class HeroStarMaxErrorException extends CustomBaseException {
    public HeroStarMaxErrorException() {
        super(HttpStatus.HERO_STAR_MAX_ERROR.getCode(), HttpStatus.HERO_STAR_MAX_ERROR.getMsg());
    }
}
