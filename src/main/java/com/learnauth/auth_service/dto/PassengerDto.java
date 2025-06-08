package com.learnauth.auth_service.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDto {
    private String name;

    private String email;

    private String password;

    private String licenseNumber;

    private String mobileNumber;

    private Date createdAt;
}
