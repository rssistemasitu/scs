package com.rogerio.servicesecurity.jwt;

import com.rogerio.servicesecurity.config.CustomUserDetails;
import com.rogerio.servicesecurity.dto.response.StatusAndRoleResponse;
import com.rogerio.servicesecurity.exception.error.ForbiddenException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.rogerio.servicesecurity.utils.Constants.FORBIDDEN_EXCEPTION_MESSAGE;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

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

    public String generateJwtToken(Authentication authentication) {

        CustomUserDetails userPrincipal = (CustomUserDetails) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationYear))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateJwtTokenForgot(String token) {
        return Jwts.builder()
                .setSubject(token)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationHour))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody().getSubject();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            throw new MalformedJwtException("Invalid JWT token.");
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            throw new com.rogerio.servicesecurity.exception.error.ExpiredJwtException("JWT token is expired.");
        } catch (io.jsonwebtoken.UnsupportedJwtException e) {
            throw new UnsupportedJwtException("JWT token is unsupported.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("JWT claims string is empty.");
        }
    }

    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

    public boolean verifyPermissionFromUserLogged(String rolePermission) {
        final CustomUserDetails userFromSession = getUserFromSession();
        Set<String> roles = userFromSession.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toSet());

        boolean hasPermission = roles.contains(rolePermission);
        if(Objects.isNull(roles) || !hasPermission) {
            throw new ForbiddenException(FORBIDDEN_EXCEPTION_MESSAGE);
        }
        return hasPermission;
    }

    public CustomUserDetails getUserFromSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userDetails;
    }

    public StatusAndRoleResponse getRoleFromUserLogged() {
        final CustomUserDetails userFromSession = getUserFromSession();


        Set<String> roles = userFromSession.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toSet());

        return StatusAndRoleResponse.builder().roles(roles).status(userFromSession.isEnabled()).build();
    }
}
