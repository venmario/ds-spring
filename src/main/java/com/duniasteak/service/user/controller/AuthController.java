package com.duniasteak.service.user.controller;

import com.duniasteak.service.user.dto.UserDto;
import com.duniasteak.service.user.mapper.UserMapper;
import com.duniasteak.service.user.model.User;
import com.duniasteak.service.user.service.AuthService;
import com.duniasteak.service.util.TemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
public class AuthController {

    @Autowired
    private TemplateResponse templateResponse;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<Map> register(@RequestBody UserDto userDto){
        ResponseEntity<Map> response;
        try{
            userDto.setId(-1);
            response = authService.register(UserMapper.INSTANCE.dtoToUser(userDto));
        }catch (Exception e){
            response = new ResponseEntity<>(templateResponse.error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @PostMapping("/login")
    public ResponseEntity<Map> login(@RequestBody UserDto userDto){
        ResponseEntity<Map> response;
        try{
            userDto.setId(-1);
            response = authService.login(UserMapper.INSTANCE.dtoToUser(userDto));
        }catch (Exception e){
            response = new ResponseEntity<>(templateResponse.error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
