package com.learnauth.auth_service.services;

import com.learnauth.auth_service.helpers.AuthUserDetails;
import com.learnauth.auth_service.models.Passenger;
import com.learnauth.auth_service.repositories.PassengerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
Responsible for loading the user in the form of UserDetails object for  authentication
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    private final PassengerRepository passengerRepository;

    public UserDetailsServiceImpl(PassengerRepository passengerRepository){
        this.passengerRepository = passengerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Passenger> passenger = passengerRepository.findPassengerByEmail(email);
        if (passenger.isPresent()){
            return new AuthUserDetails(passenger.get());
        }
        else{
            throw new UsernameNotFoundException("Email not found");
        }

    }
}
