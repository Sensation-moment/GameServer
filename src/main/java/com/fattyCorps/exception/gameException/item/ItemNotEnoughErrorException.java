package com.fattyCorps.exception.gameException.item;

import com.fattyCorps.exception.CustomBaseException;
import com.fattyCorps.obj.msg.HttpStatus;

/**
 * 道具不足异常
 */
public class ItemNotEnoughErrorException extends CustomBaseException {
    public ItemNotEnoughErrorException() {
        super(HttpStatus.ITEM_NOT_ENOUGH_ERROR.getCode(), HttpStatus.ITEM_NOT_ENOUGH_ERROR.getMsg());
    }
}
