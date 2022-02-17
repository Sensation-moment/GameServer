package com.fattyCorps.exception.gameException.item;

import com.fattyCorps.exception.CustomBaseException;
import com.fattyCorps.obj.msg.HttpStatus;

/**
 * 道具ID错误异常
 */
public class ItemIdErrorException extends CustomBaseException {
    public ItemIdErrorException() {
        super(HttpStatus.ITEM_ID_ERROR.getCode(), HttpStatus.ITEM_ID_ERROR.getMsg());
    }
}
