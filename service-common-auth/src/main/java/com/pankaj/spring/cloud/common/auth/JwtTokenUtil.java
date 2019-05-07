package com.pankaj.spring.cloud.common.auth;
import java.io.Serializable;

import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil implements Serializable {    
   /* @Value("${jwt.security.key}")
    private String jwtKey;
    private String doGenerateToken(String subject) {
        Claims claims = Jwts.claims().setSubject(subject);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("http://jwtdemo.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() +           
                          ACCESS_TOKEN_VALIDITY_SECONDS*1000))
                .signWith(SignatureAlgorithm.HS256, jwtKey)
                .compact();
    }*/
   // Other methods
}