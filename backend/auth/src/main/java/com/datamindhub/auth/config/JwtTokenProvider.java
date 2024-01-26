package com.datamindhub.auth.config;

import com.datamindhub.auth.dto.UserRequestDto;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Date;

@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtProperties jwtProperties;
    public String generateToken(UserRequestDto userRequestDto, Duration expireAt) {
        long now = new Date().getTime() + expireAt.toMillis();
        return makeToken(new Date(now), userRequestDto);
    }

    public String makeToken(Date expiry, UserRequestDto userRequestDto) {
        return Jwts.builder()
                .header().add("typ", "JWT").and()
                .issuer(jwtProperties.getIssuer())
                .issuedAt(new Date())
                .expiration(expiry)
                .subject(userRequestDto.getEmail())
                .claim("id", userRequestDto.getEmail())
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }


}
