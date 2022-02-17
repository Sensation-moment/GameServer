package com.fattyCorps.exception.gameException;

import com.fattyCorps.exception.CustomBaseException;
import com.fattyCorps.obj.msg.HttpStatus;

/**
 * Token异常
 */
public class TokenErrorException extends CustomBaseException {
    public TokenErrorException() {
        super(HttpStatus.TOKEN_ERROR.getCode(), HttpStatus.TOKEN_ERROR.getMsg());
    }
}
