package com.fattyCorps.exception.gameException.item;

import com.fattyCorps.exception.CustomBaseException;
import com.fattyCorps.obj.msg.HttpStatus;

/**
 * 道具数量异常
 */
public class ItemNumErrorException extends CustomBaseException {
    public ItemNumErrorException() {
        super(HttpStatus.ITEM_NUM_ERROR.getCode(), HttpStatus.ITEM_NUM_ERROR.getMsg());
    }
}
