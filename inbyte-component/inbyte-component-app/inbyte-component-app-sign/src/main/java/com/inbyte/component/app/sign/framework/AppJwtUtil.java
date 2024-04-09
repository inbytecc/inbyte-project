package com.inbyte.component.app.sign.framework;

import com.alibaba.fastjson2.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.inbyte.commons.util.StringUtil;
import com.inbyte.component.app.sign.framework.exception.AppTokenUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * JSON Web Token工具
 *
 * @author chenjw
 * @date 2020/8/6
 **/
public class AppJwtUtil {

    private static final Logger log = LoggerFactory.getLogger(AppJwtUtil.class);

    private static final String JWT_SECRET = "inbyte-app-jwt-secret";
    private static final String Default_Issuer = "inbyte";
    private static final String Audience = "audience";
    private static final String Bearer = "Bearer ";

    /**
     * Token有效时间一年
     */
    private static final long Token_Available_Time = 31536000000L;

    /**
     * jwt算法
     */
    private static final Algorithm JWT_Algorithm = Algorithm.HMAC256(JWT_SECRET);
    private static final JWTVerifier JWT_Verifier = JWT.require(JWT_Algorithm)
            //匹配指定的token发布者 auth0
            .withIssuer(Default_Issuer)
            .build();

    /**
     * 创建jwt
     *
     * @param subject
     * @return
     * @throws Exception
     */
    public static String createJwt(Object subject) {
        return JWT.create()
                //设置过期时间
                .withExpiresAt(new Date(System.currentTimeMillis() + Token_Available_Time))
                //设置接受方信息, 一般时登录用户
                .withAudience(Audience)
                .withIssuer(Default_Issuer)
                .withSubject(JSON.toJSONString(subject))
                //使用HMAC算法, 作为密钥加密
                .sign(Algorithm.HMAC256(JWT_SECRET));
    }

    /**
     * 验证token
     */
    public static DecodedJWT verifierToken(String jwtToken) {
        try {
            if (StringUtil.isEmpty(jwtToken)) {
                return null;
            }
            if (jwtToken.startsWith(Bearer)) {
                jwtToken = jwtToken.substring(7);
            }
            return JWT_Verifier.verify(jwtToken);
        } catch (JWTVerificationException e) {
            // 这里只做验证, 不抛异常也不做进一步记录
            // 因为日志拦截记录需要, 而使用时不影响业务
            log.info("App JWT Token签名验证异常");
            return null;
        }
    }

    /**
     * 解析对象
     *
     * @param jwtToken
     * @return
     */
    public static AppInfo parseObject(String jwtToken) {
        DecodedJWT jwt = verifierToken(jwtToken);
        if (jwt == null) {
            throw new AppTokenUnavailableException();
        }
        return JSON.parseObject(jwt.getSubject(), AppInfo.class);
    }

}
