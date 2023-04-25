package top.hxyac.chatbot.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * Jwt工具类
 */
public class JWTUtils {

    /**
     * 过期时间 7天
     */
    private static final long EXPIRE = 60000 * 60 * 24 * 7;

    /**
     * 加密密钥
     */
    private static final String SECRET = "5c47SPKVC7VwCRKKBlMMA";

    /**
     *令牌前缀
     */
    private static final String TOKEN_PREFIX = "hxyac";

    /**
     * subject
     */
    private static final String SUBJECT = "hxy";

    /**
     * 根据用户信息生成令牌
     * @return
     */
    public static String geneJsonWebToken() {
        String token = Jwts.builder().setSubject(SUBJECT)
                .claim("now_time",new Date())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE))
                .signWith(SignatureAlgorithm.HS256,SECRET).compact();
        token = TOKEN_PREFIX + token;
        return token;
    }

    /**
     * 校验token的方法
     * @param token
     * @return
     */
    public static Claims checkJWT(String token){
         try {
             Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX,"")).getBody();
             return claims;
         }catch (Exception e){
             return null;
         }
    }
}
