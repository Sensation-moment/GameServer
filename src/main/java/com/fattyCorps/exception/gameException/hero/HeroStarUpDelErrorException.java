package com.fattyCorps.exception.gameException.hero;

import com.fattyCorps.exception.CustomBaseException;
import com.fattyCorps.obj.msg.HttpStatus;

public class HeroStarUpDelErrorException extends CustomBaseException {
    public HeroStarUpDelErrorException() {
        super(HttpStatus.HERO_STAR_UP_DEL_ERROR.getCode(), HttpStatus.HERO_STAR_UP_DEL_ERROR.getMsg());
    }
}