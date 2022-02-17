package com.fattyCorps.exception.gameException.hero;

import com.fattyCorps.exception.CustomBaseException;
import com.fattyCorps.obj.msg.HttpStatus;


public class HeroStarStoneNotEnoughErrorException extends CustomBaseException {
    public HeroStarStoneNotEnoughErrorException() {
        super(HttpStatus.HERO_STAR_STONE_NOT_ENOUGH_ERROR.getCode(), HttpStatus.HERO_STAR_STONE_NOT_ENOUGH_ERROR.getMsg());
    }
}
