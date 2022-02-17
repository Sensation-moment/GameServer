package com.fattyCorps.exception.gameException.hero;

import com.fattyCorps.exception.CustomBaseException;
import com.fattyCorps.obj.msg.HttpStatus;

/**
 * 升星原材料异常
 */
public class HeroStarUpMaterialErrorException extends CustomBaseException {
    public HeroStarUpMaterialErrorException() {
        super(HttpStatus.HERO_STAR_UP_MATERIAL_ERROR.getCode(), HttpStatus.HERO_STAR_UP_MATERIAL_ERROR.getMsg());
    }
}