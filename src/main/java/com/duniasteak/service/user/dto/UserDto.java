package com.duniasteak.service.user.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto {
    private Integer id;
    private String username;
    private String firstname;
    private String lastname;
    private String phonenumber;
    private String email;
    private String password;
    private String poin;
    private String rolg;
}
