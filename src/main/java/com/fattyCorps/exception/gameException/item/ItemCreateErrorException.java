package com.fattyCorps.exception.gameException.item;

import com.fattyCorps.exception.CustomBaseException;
import com.fattyCorps.obj.msg.HttpStatus;

/**
 * 道具创建错误异常
 */
public class ItemCreateErrorException extends CustomBaseException {
    public ItemCreateErrorException() {
        super(HttpStatus.ITEM_CREATE_ERROR.getCode(), HttpStatus.ITEM_CREATE_ERROR.getMsg());
    }
}
