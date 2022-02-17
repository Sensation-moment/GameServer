package com.fattyCorps.exception.gameException;

import com.fattyCorps.exception.CustomBaseException;
import com.fattyCorps.obj.msg.HttpStatus;

/**
 * 金币不足异常
 */
public class CoinNotEnoughErrorException extends CustomBaseException {
    public CoinNotEnoughErrorException() {
        super(HttpStatus.COIN_NOT_ENOUGH_ERROR.getCode(), HttpStatus.COIN_NOT_ENOUGH_ERROR.getMsg());
    }
}
