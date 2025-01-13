package com.duniasteak.service.request.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LoginRequest {
    String username;
    String password;
    String oldFcmToken;
    String currentFcmToken;
}
