package com.learnauth.auth_service.controllers;

import com.learnauth.auth_service.dto.AuthRequestDto;
import com.learnauth.auth_service.dto.PassengerDto;
import com.learnauth.auth_service.dto.PassengerSignupRequestDto;
import com.learnauth.auth_service.dto.TokenAuthRequest;
import com.learnauth.auth_service.services.AuthService;
import com.learnauth.auth_service.services.JwtService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;


    private AuthController(AuthService authService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup/passenger")
    public ResponseEntity<?> signUp(@RequestBody PassengerSignupRequestDto passengerSignupRequestDto) {
        PassengerDto response = authService.passengerSignup(passengerSignupRequestDto);
        System.out.println(response);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(201));
    }

    @PostMapping("/gettoken")
    public ResponseEntity<String> getToken(@RequestBody TokenAuthRequest tokenAuthRequest) {
        Map<String, Object> mp = new HashMap<>();
        mp.put("username", tokenAuthRequest.getUsername());
        mp.put("password", tokenAuthRequest.getPassword());
        String token = jwtService.generateToken(mp, tokenAuthRequest.getUsername());
        return new ResponseEntity<>(token, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validate(HttpServletRequest req){
        for(Cookie cookie : req.getCookies()){
            System.out.println(cookie.getName() +" || "+ cookie.getAttributes()
                                + " || " +cookie.getValue());
        }
        return new ResponseEntity<>("Success",HttpStatusCode.valueOf(200));
    }

    @PostMapping("/signin/passenger")
    public ResponseEntity<?>  signIn (@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword()));
        if(authentication.isAuthenticated()){
            // generate the JWT token
            String token = jwtService.generateToken(authentication.getName());
            System.out.println(token);

            // Set the jwt token in a cookie
            ResponseCookie cookie = ResponseCookie.from("jwtToken",token)
                    .secure(false)
                    .httpOnly(false)
                    .path("/")
                    .maxAge(3600)
                    .build();

//            Cookie cookie = new Cookie("jwt-token",token);
//            cookie.setSecure(false);
//            cookie.setPath("/");
//            cookie.setHttpOnly(true);
//            cookie.setMaxAge(60);
//            response.addCookie(cookie);
            response.setHeader(HttpHeaders.SET_COOKIE,cookie.toString());
            System.out.println(authentication.isAuthenticated());
            System.out.println(authentication.getCredentials());
            System.out.println(authentication.getPrincipal());

            return new ResponseEntity<>(token,HttpStatusCode.valueOf(200));
        }
        return new ResponseEntity<>("Failed User Authentication", HttpStatusCode.valueOf(403));
    }
}

