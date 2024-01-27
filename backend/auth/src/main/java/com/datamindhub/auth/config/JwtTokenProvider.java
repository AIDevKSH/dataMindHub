package com.datamindhub.auth.config;

import com.datamindhub.auth.dto.UserRequestDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;

    public String generateToken(UserRequestDto userRequestDto, Duration expireAt) {
        Date expiry = new Date(new Date().getTime() + expireAt.toMillis());
        SecretKey secretKey = getSecretKey();;

        return Jwts.builder()
                .header().add("typ", "JWT").and()
                .issuer(jwtProperties.getIssuer())  // 발급자
                .issuedAt(new Date())  // 발급일
                .expiration(expiry)  // 만료일
                .subject(userRequestDto.getEmail())  // 내용
                .claim("email", userRequestDto.getEmail())  // 클레임
                .signWith(secretKey, Jwts.SIG.HS256)  // 서명
                .compact();
    }

    public String generateDefaultToken(UserRequestDto userRequestDto) {
        return generateToken(userRequestDto, Duration.ofHours(2));  // 기본 만료 시간은 2시간
    }

    public String validToken(String token) {
        SecretKey secretKey = getSecretKey();
        Claims claims;

        claims = Jwts.parser()
                .verifyWith(secretKey).build()
                .parseSignedClaims(token)
                .getPayload();

        if (claims.getExpiration().before(new Date())) {  // 토큰이 만료됐을 때
            return null;
        }
        return claims.get("email", String.class);
    }

    private SecretKey getSecretKey() {
        SecretKey secretKey = null;
        try {
            secretKey = Keys.hmacShaKeyFor(
                    jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8)
            );
        } catch (WeakKeyException e) {
            log.info("토큰 키 설정 실패", e);
            throw new BadCredentialsException("토큰 키 설정 실패", e);
        }
        return secretKey;
    }
}
