package com.fattyCorps.utils;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fattyCorps.exception.gameException.TokenErrorException;

import javax.servlet.http.HttpServletRequest;

/**
 * Servlet工具类(主要用于获取token中的id)
 */
public class ServletUtils {
    public static Integer getAccIdByRequest(HttpServletRequest request) {
        return getIdByRequest(request, JwtUtil.ACC_ID_NAME);
    }

    public static Integer getPlayerIdByRequest(HttpServletRequest request) {
        return getIdByRequest(request, JwtUtil.PLAYER_ID_NAME);
    }

    /**
     * 解码Token获取用户ID
     *
     * @param request
     * @param idName
     * @return
     */
    private static Integer getIdByRequest(HttpServletRequest request, String idName) {
        String token = request.getHeader("token");
        if (token == null) {
            throw new TokenErrorException();
        }
        DecodedJWT decodedJWT;
        try {
            decodedJWT = JwtUtil.verify(token);
        } catch (Exception e) {
            throw new TokenErrorException();
        }

        Integer id = decodedJWT.getClaim(idName).asInt();
        // 判空
        if (id == null) {
            throw new TokenErrorException();
        }
        return id;
    }
}
