package com.learnauth.auth_service.services;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.web.webauthn.api.Bytes;
import org.springframework.stereotype.Service;

import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService implements CommandLineRunner {

    @Value("${jwt.expiry}")
    private int jwtExpiry;

    @Value("${jwtKey}")
    private String secret;

    private Date getExpiryDate(){
        return new Date((new Date().getTime() + jwtExpiry* 1000L));
    }

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    // Retrieving any information from token
    public Claims getALlClaimsFromToken(String token){
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
//    extract claims from the token
    public <T> T getClaimsFromToken(String token, Function<Claims,T> claimsResolver){
        final Claims claims = getALlClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public String getUsername(String token){
        return getClaimsFromToken(token,Claims::getSubject);
    }
    public Date getExpiration(String token){
        return getClaimsFromToken(token, Claims::getExpiration);
    }
    public Boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token,String email){
        String fetchedEmailFromJWT = getUsername(token);
        return (fetchedEmailFromJWT.equals(email)) && !isTokenExpired(token);
    }

    public String generateToken(Map<String,Object> payload,String username){
        Date expiryDate = getExpiryDate();
        SecretKey key = getSecretKey();
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expiryDate)
                .signWith(key)
                .claims(payload)
                .compact();
    }

    public String generateToken(String username){
        return generateToken(new HashMap<>(),username);
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String, Object> mp = new HashMap<>();
        mp.put("email","xyz@hotmail.com");
        mp.put("name","xyz");
        mp.put("age",10);
        String token = generateToken(mp,"test39");
        System.out.println("Token: "+ token);
        System.out.println("Parsed Body: "+ getALlClaimsFromToken(token));
        System.out.println("Username: "+ getUsername(token));
        System.out.println("Token will expire at: "+ getExpiration(token));
    }
}
