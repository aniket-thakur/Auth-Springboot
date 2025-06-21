package com.learnauth.auth_service.dto;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenAuthRequest {
    private String username;
    private String password;
}
