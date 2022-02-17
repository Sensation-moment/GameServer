package com.fattyCorps.exception.gameException.hero;

import com.fattyCorps.exception.CustomBaseException;
import com.fattyCorps.obj.msg.HttpStatus;


/**
 * 英雄经验不足异常
 */
public class HeroExpNotEnoughErrorException extends CustomBaseException {
    public HeroExpNotEnoughErrorException() {
        super(HttpStatus.HERO_EXP_NOT_ENOUGH_ERROR.getCode(), HttpStatus.HERO_EXP_NOT_ENOUGH_ERROR.getMsg());
    }
}
