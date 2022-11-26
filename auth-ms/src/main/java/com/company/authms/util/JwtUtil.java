package com.company.authms.util;

import com.company.authms.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${token.secret}")
    private String secret;

    @Value("${token.access-token-expiration-time}")
    private String accessTokenExpTime;

    @Value("${token.refresh-token-expiration-time}")
    private String refreshTokenExpTime;


    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public String generateJwt(Map<String, Object> userData, String tokenExpTime){
        return Jwts.builder()
                .claim("email", userData.get("email"))
                .claim("roles", userData.get("roles").toString())
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(tokenExpTime)))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String generateAccessToken(Map<String, Object> userData) {
        return generateJwt(userData, accessTokenExpTime);
    }


    public String generateAccessTokenFromUser(UserEntity user) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", user.getEmail());
        map.put("roles", user.getAuthorities());
        return "Bearer " + generateAccessToken(map);
    }

    public String generateRefreshTokenFromUser(UserEntity user) {
        Map<String, Object> map = new HashMap<>();
        map.put("email", user.getEmail());
        map.put("roles", user.getAuthorities());
        return "Bearer " + generateJwt(map, refreshTokenExpTime);
    }

    public String generateAccessTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("No header token");
        }

        String refreshToken = authorizationHeader.split("Bearer ")[1];
        Map<String, Object> map = extractAllClaims(refreshToken);

        return "Bearer " + generateAccessToken(map);
    }
}
