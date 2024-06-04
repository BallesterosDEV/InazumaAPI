package com.ballesteros.api.security.auth.service;

import com.ballesteros.api.persistence.models.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    private static final String SECRET_KEY = "xgQr0HXrXiFMaWZ4qhntdlVamtI4ixcaEH+qmqLgd8o=";

    public String getToken(UserModel user) {
        String token = getToken(Map.of("username", user.getUsername()), user);
        return token;
    }

    public String getToken(Map<String, Object> extraClaims, UserModel user) {
        String token = Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // 24 horas
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return token;
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) {
        String username = getClaim(token, Claims::getSubject);
        System.out.println("Username from Token: " + username);
        return username;
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        boolean isValid = (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        System.out.println("Is token valid: " + isValid);
        return isValid;
    }

    private Claims getAllClaims(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(getKey())
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        boolean isExpired = getExpiration(token).before(new Date());
        System.out.println("Is token expired: " + isExpired);
        return isExpired;
    }
}