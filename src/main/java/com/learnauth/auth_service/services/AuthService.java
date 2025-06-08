package com.learnauth.auth_service.services;

import com.learnauth.auth_service.dto.PassengerDto;
import com.learnauth.auth_service.dto.PassengerSignupRequestDto;
import com.learnauth.auth_service.models.Passenger;
import com.learnauth.auth_service.repositories.PassengerRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final PassengerRepository passengerRepository;

    public AuthService(PassengerRepository passengerRepository){
        this.passengerRepository = passengerRepository;
    }
    public PassengerDto passengerSignup(PassengerSignupRequestDto passengerSignupRequestDto){

        Passenger passenger =  Passenger.builder()
                .Name(passengerSignupRequestDto.getName())
                .email(passengerSignupRequestDto.getEmail())

//                :TODO -> Encrypt the below password
                .password(passengerSignupRequestDto.getPassword())

                .mobileNumber(passengerSignupRequestDto.getMobileNumber())
                .build();

        Passenger newPassenger = passengerRepository.save(passenger);

        return PassengerDto.from(newPassenger);



    }
}
