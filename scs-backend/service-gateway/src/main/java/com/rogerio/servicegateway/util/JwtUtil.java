package com.rogerio.servicegateway.util;

import com.rogerio.servicegateway.model.UserInfo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.security.Key;

@Component
public class JwtUtil {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtExpirationHour}")
    private long jwtExpirationHour;

    @Value("${app.jwtExpirationDay}")
    private long jwtExpirationDay;

    @Value("${app.jwtExpirationMonth}")
    private long jwtExpirationMonth;

    @Value("${app.jwtExpirationYear}")
    private long jwtExpirationYear;

    @Autowired
    private RestTemplate restTemplate;

    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateJwtToken(String token) {
        try {
            final Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            throw new MalformedJwtException("Invalid JWT token.");
        } catch (io.jsonwebtoken.UnsupportedJwtException e) {
            throw new UnsupportedJwtException("JWT token is unsupported.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("JWT claims string is empty.");
        }
    }

    public String parseJwt(String token) {

        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }

    public Claims getEmailFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
    }

    public ResponseEntity<UserInfo> getStatusAndRoles(String authHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, authHeader);
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        // Construir a solicitação HTTP GET
        RequestEntity<?> requestEntity = RequestEntity
                .get("http://localhost:9193/api/v1/user/user-auth-info")
                .headers(headers)
                .build();

        // Executar a solicitação e obter a resposta
        ResponseEntity<UserInfo> response = restTemplate.exchange(requestEntity, UserInfo.class);
        return response;
    }
}
