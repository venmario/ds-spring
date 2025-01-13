package com.duniasteak.service.user.controller;

import com.duniasteak.service.user.dto.UserDto;
import com.duniasteak.service.user.mapper.UserMapper;
import com.duniasteak.service.user.service.AuthService;
import com.duniasteak.service.util.TemplateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private TemplateResponse templateResponse;

    @Autowired
    private AuthService authService;

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/register")
    public ResponseEntity<Map> register(@Valid @RequestBody UserDto userDto, BindingResult bindingResult){
        ResponseEntity<Map> response;
        try{
            //check if the validation has error
            if (bindingResult.hasErrors()){
                Map<String, String> errors = new HashMap<>();
                bindingResult.getFieldErrors().forEach(error -> {
                    errors.put(error.getField(), error.getDefaultMessage());
                });
                Map<String, Object> registerResponse = new HashMap<>();
                registerResponse.put("success", false);
                registerResponse.put("errorMessage", errors);
                registerResponse.put("code", HttpStatus.BAD_REQUEST.value());
                return new ResponseEntity<>(registerResponse, HttpStatus.BAD_REQUEST);
            }
            logger.info("register");
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
            logger.info("login");
            userDto.setId(-1);
            response = authService.login(UserMapper.INSTANCE.dtoToUser(userDto));
        }catch (Exception e){
            response = new ResponseEntity<>(templateResponse.error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
