package com.italycalibur.ciallo.common.utils;

import com.italycalibur.ciallo.common.configuration.properties.JwtTokenProperty;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * @author dhr
 * @version 1.0
 * @date 2025-02-09 16:27:58
 * @description: jwt工具类
 */
@Component
public class JwtUtils {
    @Resource
    private JwtTokenProperty jwtTokenProperty;

    /**
     * 生成jwt到期时间
     * @return Date
     */
    private Date generateExp() {
        long expTimeMillis = System.currentTimeMillis() + jwtTokenProperty.getExpireTime();
        return new Date(expTimeMillis);
    }

    /**
     * 生成密钥
     * @return SecretKey
     */
    private SecretKey generateKey() {
        return Keys.hmacShaKeyFor(jwtTokenProperty.getSecretKey().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成token
     * 使用HS256算法, 密钥和有效时间从配置文件上取
     * @param claims 设置的信息
     * @return String
     */
    public String createToken(Map<String, Object> claims) {
        // 过期时间
        Date exp = generateExp();
        // 密钥，根据提供的字节数组长度选择适当的 HMAC 算法，并返回相应的 SecretKey 对象。
        SecretKey key = generateKey();
        // 设置jwt的body
        return Jwts.builder()
                // 设置签名使用的签名算法和签名使用的秘钥
                .signWith(key)
                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .claims(claims)
                // 设置过期时间
                .expiration(exp)
                // 返回token
                .compact();
    }

    /**
     * 解析token
     * @param token 令牌字符串
     * @return Claims
     */
    public Claims parseToken(String token) {
        if (token.contains(jwtTokenProperty.getPrefix())) {
            token = token.substring(jwtTokenProperty.getPrefix().length());
        }
        // 密钥
        SecretKey key = generateKey();
        // 得到解析对象
        JwtParser parser = Jwts.parser().verifyWith(key).build();
        // 得到需要的信息
        return parser.parseSignedClaims(token).getPayload();
    }
}
