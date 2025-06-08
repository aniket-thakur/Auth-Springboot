package com.learnauth.auth_service.dto;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerSignupRequestDto {
    private String id;

    private String email;

    private String name;

    private String password;

    private String mobileNumber;

   
}
