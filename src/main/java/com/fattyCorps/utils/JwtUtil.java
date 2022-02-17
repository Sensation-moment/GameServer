package com.fattyCorps.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;

/**
 * JWT加密工具类
 */
public class JwtUtil {
    // 秘钥
    private static final String JWT_SECRET = "vcf&*sgh$ff";

    public static final String ACC_ID_NAME = "id";
    public static final String PLAYER_ID_NAME = "player_id";

    public static String createAccToken(int id){
        return createToken(ACC_ID_NAME, id);
    }

    public static String createPlayerToken(int id){
        return createToken(PLAYER_ID_NAME, id);
    }

    /**
     * 根据用户id与username生成token
     *
     * @param idName
     * @param id
     * @return
     */
    private static String createToken(String idName, int id){
        // 签发时间
        Calendar ins = Calendar.getInstance();
        ins.add(Calendar.SECOND, 24*60*60);

        // 秘钥加密方式
        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);

        // 生成token
        String token = JWT.create()
                .withClaim(idName, id)
                .withExpiresAt(ins.getTime())
                .sign(algorithm);

        return token;
    }

    /**
     * 获取token信息
     *
     * @param token
     * @return
     */
    public static DecodedJWT verify(String token){
        return JWT.require(Algorithm.HMAC256(JWT_SECRET)).build().verify(token);
    }
}