package com.kirakira.server.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT 工具：签发 token / 校验 token。
 *
 * token 结构：header.payload.signature
 * - header   ：算法（HS256）
 * - payload  ：uid、username、过期时间（明文可被解码，别放密码）
 * - signature：用服务端密钥算出的签名，篡改 payload 会导致签名对不上
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expire-hours}")
    private long expireHours;

    private SecretKey key() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /** 签发 token：把 uid 放进 subject，username 放进自定义 claim */
    public String createToken(Integer uid, String username) {
        Date now = new Date();
        Date expire = new Date(now.getTime() + expireHours * 3600_000L);

        return Jwts.builder()
                .setSubject(String.valueOf(uid))
                .claim("username", username)
                .setIssuedAt(now)
                .setExpiration(expire)
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 解析 token，返回 uid；无效或过期返回 null。
     * 注意：这里把所有异常都吞掉返回 null，调用方只需要判断 null。
     */
    public Integer parseUid(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return Integer.valueOf(claims.getSubject());
        } catch (Exception e) {
            return null;
        }
    }
}
