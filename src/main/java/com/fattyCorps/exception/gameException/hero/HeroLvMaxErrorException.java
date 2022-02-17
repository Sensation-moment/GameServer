package com.fattyCorps.exception.gameException.hero;

import com.fattyCorps.exception.CustomBaseException;
import com.fattyCorps.obj.msg.HttpStatus;

/**
 * 英雄最大级别异常
 */
public class HeroLvMaxErrorException extends CustomBaseException {
    public HeroLvMaxErrorException() {
        super(HttpStatus.HERO_LV_MAX_ERROR.getCode(), HttpStatus.HERO_LV_MAX_ERROR.getMsg());
    }
}
