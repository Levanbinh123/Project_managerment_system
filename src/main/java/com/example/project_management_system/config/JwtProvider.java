package com.example.project_management_system.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Collection;
import java.util.Date;

public class JwtProvider {
    static SecretKey key= Keys.hmacShaKeyFor(JwtConstant.SECRET.getBytes());
    public static String generateToken(Authentication authentication) {
        //Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        return Jwts.builder().setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime()+86400000))
                .claim("email", authentication.getName())
                .signWith(key)
                .compact();
    }
    public static String getEmailFormToken(String token) {
        token = token.substring(7);
        Claims claims=Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        String email=String.valueOf(claims.get("email"));
        return email;
    }
}
