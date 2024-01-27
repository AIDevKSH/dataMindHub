package com.datamindhub.auth.config;

import com.datamindhub.auth.dto.UserRequestDto;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;
import jakarta.websocket.Decoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.beans.Encoder;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;

    public String makeToken(UserRequestDto userRequestDto, Duration expireAt) {
        Date expiry = new Date(new Date().getTime() + expireAt.toMillis());
        SecretKey secretKey = getSecretKey();;

        return Jwts.builder()
                .header().add("typ", "JWT").and()
                .issuer(jwtProperties.getIssuer())  // 발급자
                .issuedAt(new Date())  // 발급일
                .expiration(expiry)  // 만료일
                .subject(userRequestDto.getEmail())  // 내용
                .claim("id", userRequestDto.getEmail())  // 클레임
                .signWith(secretKey, Jwts.SIG.HS256)  // 서명
                .compact();
    }

    public String makeDefaultToken(UserRequestDto userRequestDto) {
        return makeToken(userRequestDto, Duration.ofHours(2));  // 기본 만료 시간은 2시간
    }

    public boolean isValidToken(String token) {
        SecretKey secretKey = getSecretKey();
        try {
            Jwts.parser()
                    .verifyWith(secretKey).build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private SecretKey getSecretKey() {
        SecretKey secretKey = null;
        try {
            secretKey = Keys.hmacShaKeyFor(

                    jwtProperties.getSecretKey() .getBytes(StandardCharsets.UTF_8)
            );
        } catch (WeakKeyException e) {
            log.info("토큰 키 설정 실패", e);
            throw new BadCredentialsException("토큰 키 설정 실패", e);
        }
        return secretKey;
    }
}
