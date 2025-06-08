package com.learnauth.auth_service.dto;

import com.learnauth.auth_service.models.Passenger;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDto {
    private Long id;

    private String name;

    private String email;

    private String password;

    private String mobileNumber;

    private Date createdAt;

    public static PassengerDto from(Passenger p){

        PassengerDto result = PassengerDto
                .builder()
                .id(p.getId())
                .name(p.getName())
                .email(p.getEmail())
                .password(p.getPassword())
                .mobileNumber(p.getMobileNumber())
                .createdAt(p.getCreatedAt())
                .build();

        return result;
    }
}
