package com.learnauth.auth_service.controllers;

import com.learnauth.auth_service.dto.PassengerDto;
import com.learnauth.auth_service.dto.PassengerSignupRequestDto;
import com.learnauth.auth_service.dto.TokenAuthRequest;
import com.learnauth.auth_service.services.AuthService;
import com.learnauth.auth_service.services.JwtService;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;
    private JwtService jwtService;


    private AuthController(AuthService authService, JwtService jwtService){
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<?> signUp(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto){
        PassengerDto response = authService.passengerSignup(passengerSignupRequestDto);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(201));
    }

    @PostMapping("/gettoken")
    public ResponseEntity<String> getToken(@RequestBody TokenAuthRequest tokenAuthRequest ){
        Map<String, Object> mp = new HashMap<>();
        mp.put("username",tokenAuthRequest.getUsername());
        mp.put("password", tokenAuthRequest.getPassword());
        String token = jwtService.generateToken(mp,tokenAuthRequest.getUsername());
        return new ResponseEntity<>(token,HttpStatusCode.valueOf(200)) ;

    }
}
