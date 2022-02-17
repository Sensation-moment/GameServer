package com.fattyCorps.exception.gameException.hero;

import com.fattyCorps.exception.CustomBaseException;
import com.fattyCorps.obj.msg.HttpStatus;

/**
 * 英雄模板ID错误异常
 */
public class HeroMouldIdErrorException extends CustomBaseException {
    public HeroMouldIdErrorException() {
        super(HttpStatus.HERO_MOULD_ID_ERROR.getCode(), HttpStatus.HERO_MOULD_ID_ERROR.getMsg());
    }
}
