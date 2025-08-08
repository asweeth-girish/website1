package org.example.security;


import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import io.jsonwebtoken.security.SignatureException;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JwtUtil {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;

    public static String generateToken(String username, List<String> roles) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("roles",roles);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)

                .compact();
    }

    public String extractUsername(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }catch (JwtException e){
            System.out.println("failed to extract username from token"+e.getMessage());
            return null;
        }
    }

    public boolean isTokenExpired(String token) {

        try {
            Date expiration = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return expiration.before(new Date());
        }catch(JwtException e){
            System.out.println("failed to check token expiration:"+e.getMessage());
            return true;
        }
    }


    public boolean validateToken(String token,String username) {
        try{
            String  extractusername = extractUsername(token);
            if(extractusername == null || !extractusername.equals(username) ){
                throw new JwtException("Username does not match");
            }
            if(isTokenExpired(token)){
                throw new ExpiredJwtException(null, null, "Token expired");
            }
            return true;
        }
        catch(JwtException e){
            throw e;
        }

    }


}