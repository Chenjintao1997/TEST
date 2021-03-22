package com.cjt.test.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: chenjintao
 * @Date: 2020/9/11 16:26
 */
public class JwtUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);
    public static final String JWT_KEY = "Authorization";
    /**
     * 加密密文
     */

    // 生成签名的时候使用的秘钥secret，切记这个秘钥不能外露
    public static final byte[] JWT_SECRET;
    public static final String JWT_ISSUER = "new_ccas";
    public static final String JWT_SUBJECT = "session_info";
    //jwt 的缓存时间
    public static final int JWT_TTL = 30 * 60 * 1000;  //millisecond


    /**
     * 由字符串生成加密key
     *
     * @return
     */
    static {
        String stringKey = IdWorker.nextStringId();
        JWT_SECRET = stringKey.getBytes();

    }


    /**
     * 创建jwt
     */
    public static String createJWT() {

        String jwtId = IdWorker.nextStringId();
        return createJWT(jwtId, JWT_ISSUER, JWT_SUBJECT, JWT_TTL, null);
    }

    public static String createJWT(Map<String, Object> claims) {

        String jwtId = IdWorker.nextStringId();
        return createJWT(jwtId, JWT_ISSUER, JWT_SUBJECT, JWT_TTL, claims);
    }


    /**
     * 创建jwt
     */
    public static String createJWT(String id, String issuer, String subject, long ttlMillis, Map<String, Object> claims) {

        // 生成JWT的时间
        Date now = new Date();

        JWTCreator.Builder jwtBuilder = JWT.create();


        jwtBuilder.withJWTId(id)
                .withIssuer(issuer)
                .withIssuedAt(now)
                .withSubject(subject)
                .withExpiresAt(new Date(now.getTime() + ttlMillis));     //设置过期时间

        //添加自定义数据
        if (claims != null && !claims.isEmpty()) {
            for (String key : claims.keySet()) {
                Object value = claims.get(key);
                if (value instanceof Boolean) {
                    jwtBuilder.withClaim(key, (Boolean) value);
                } else if (value instanceof Integer) {
                    jwtBuilder.withClaim(key, (Integer) value);
                } else if (value instanceof Long) {
                    jwtBuilder.withClaim(key, (Long) value);
                } else if (value instanceof Double) {
                    jwtBuilder.withClaim(key, (Double) value);
                } else if (value instanceof String) {
                    jwtBuilder.withClaim(key, (String) value);
                } else if (value instanceof Date) {
                    jwtBuilder.withClaim(key, (Date) value);
                } else if (value instanceof Map) {
                    jwtBuilder.withClaim(key, (Map) value);
                } else if (value instanceof List<?>) {
                    jwtBuilder.withClaim(key, (List<?>) value);
                }

            }
        }

        return jwtBuilder.sign(Algorithm.HMAC256(JWT_SECRET));                     //声明签名加密算法
    }

    /**
     * 解密jwt
     */
    public static Map<String, Claim> parseClaimsJWT(String token) {

        DecodedJWT decodedJWT = JWT.decode(token);

        return decodedJWT.getClaims();
    }

    public static DecodedJWT parseJWT(String token) {

        return JWT.decode(token);

    }

    /**
     * 验证档次请求是否可用， 这里header authorization,是独存，独校验。跟其他无关
     */
//    public static boolean jwtValidate(RedisManager redisManager, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
//        String jwtKey = ConstantsUtil.Common.AUTHORIZATION;
//        //jwt Id
//        String jwt = httpServletRequest.getHeader(jwtKey);
//        //老的
//        String oldJwtKey = OLD_JWT_KEY + jwt;
//
//        if (StringUtils.isBlank(jwt)) {
//            return false;
//        }
//        try {
//            Claims parseJWT = JwtUtil.parseJWT(jwt);
//            if (parseJWT != null) {
//                return true;
//            }
//        } catch (io.jsonwebtoken.ExpiredJwtException e) {
//            /**
//             * 分几种情况
//             * 1.正常超时(服务端token时间到):重新生成一个新的token给前端，下次带新的token过来
//             * 2.非正常超时(用户隔了很长时间没有时间):重新生成一个新的token给前端，下次带新的token过来
//             * 3.并发情况考虑(当超时，第一个请求，第二个请求会同时过来)
//             * 4.同一用户多方登陆：目前只允许同一个用户一方登陆。多方登陆支持。这里只校验jwt失效问题。
//             *
//             *
//             */
//            try {
//                Claims c = e.getClaims();
//                String id = c.getId();
//                String subject = c.getSubject();
//                String issuer = c.getIssuer();
//
//                String lastJwtKey = USER_JWT_KEY + id;
//
//                //先判断是否是最近的一次jwt,如果不是最新的jwt，返回false
//                String redisLastJwtVal = redisManager.get(lastJwtKey, String.class);
//                if (!jwt.equals(redisLastJwtVal)) {
//                    log.error("当前访问的jwt不是最新的：{}", redisLastJwtVal);
//                    return false;
//                }
//
//                log.info("jwt 超时 {}", jwt);
//                //当存在鬓发请求时  如果老的jwtId存在.解决同一页面并发请求
//                if (redisManager.setnx(oldJwtKey, "", OLD_JWT_TTL) != 1) {
//                    return true;
//                }
//
//                //从新生成jwt id
//                String sessionId = JwtUtil.createJWT(id.toString(), issuer, subject, JwtUtil.JWT_TTL);
//                //TODO设置新的 sessionId 到httpServletResponse， Header 中 返回给前端用
//                //TODO设置新的 sessionId 到 httpServletRequest  给后面的程序用
//                httpServletResponse.setHeader(jwtKey, sessionId);
//
//                //记录最后一次jwt
//                redisManager.set(lastJwtKey, sessionId);
//                return true;
//            } catch (Exception e1) {
//                log.error(e1.getMessage(), e1);
//            }
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            throw new DominosRunTimeException(ResultMsg.USER_LOGIN_INVALID);
//        }
//        return false;
//    }
    public static boolean verifierToken(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        String token = httpServletRequest.getHeader(JWT_KEY);
        return verifierToken(token);
    }

    public static boolean verifierToken(String token) {

        if (StringUtils.isBlank(token)) {
            return false;
        }

        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWT_SECRET)).build();
        //todo 验证该验证是否会自动验证会话过期
        try {
            DecodedJWT result = verifier.verify(token);
            System.out.println(result);
        } catch (SignatureVerificationException e) {
            //todo throw exception
            LOGGER.error("token无效", e);
            return false;
        } catch (TokenExpiredException e){
            LOGGER.error("token已过期，会话已失效", e);
            //todo throw exception
            return false;
        } catch(RuntimeException e) {
            LOGGER.error("验证失败，请检查token是否正确", e);
            //todo throw exception
            return false;
        }
        return true;
    }
}
