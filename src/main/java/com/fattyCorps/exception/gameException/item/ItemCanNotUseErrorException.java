package com.fattyCorps.exception.gameException.item;

import com.fattyCorps.exception.CustomBaseException;
import com.fattyCorps.obj.msg.HttpStatus;

/**
 * 道具无法使用异常
 */
public class ItemCanNotUseErrorException extends CustomBaseException {
    public ItemCanNotUseErrorException() {
        super(HttpStatus.ITEM_CANNOT_USE_ERROR.getCode(), HttpStatus.ITEM_CANNOT_USE_ERROR.getMsg());
    }
}
