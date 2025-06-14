package com.learnauth.auth_service.services;
import java.util.Date;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.web.webauthn.api.Bytes;
import org.springframework.stereotype.Service;

import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService implements CommandLineRunner {

    @Value("${jwt.expiry}")
    private int jwtExpiry;

    @Value("${jwtKey}")
    private String secret;


    private String generateToken(Map<String,Object> payload,String username){
        Date expiryDate = new Date((new Date().getTime()+jwtExpiry*1000L));
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expiryDate)
                .signWith(key)
                .claims(payload)
                .compact();
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String, Object> mp = new HashMap<>();
        mp.put("email","xyz@hotmail.com");
        mp.put("name","xyz");
        mp.put("age",10);
        String token = generateToken(mp,"xyz01");
        System.out.println("Token: "+ token);
    }
}
