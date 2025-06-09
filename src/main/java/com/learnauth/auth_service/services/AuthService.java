package com.learnauth.auth_service.services;

import com.learnauth.auth_service.dto.PassengerDto;
import com.learnauth.auth_service.dto.PassengerSignupRequestDto;
import com.learnauth.auth_service.models.Passenger;
import com.learnauth.auth_service.repositories.PassengerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final PassengerRepository passengerRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(PassengerRepository passengerRepository, BCryptPasswordEncoder bc){
        this.passengerRepository = passengerRepository;
        this.bCryptPasswordEncoder = bc;
    }
    public PassengerDto passengerSignup(PassengerSignupRequestDto passengerSignupRequestDto){

        Passenger passenger =  Passenger.builder()
                .Name(passengerSignupRequestDto.getName())
                .email(passengerSignupRequestDto.getEmail())
                .password(bCryptPasswordEncoder.encode(passengerSignupRequestDto.getPassword()))
                .mobileNumber(passengerSignupRequestDto.getMobileNumber())
                .build();

        Passenger newPassenger = passengerRepository.save(passenger);

        return PassengerDto.from(newPassenger);



    }
}
