package com.duniasteak.service.user.service;

import com.duniasteak.service.user.model.User;
import com.duniasteak.service.user.repo.UserRepository;
import com.duniasteak.service.util.TemplateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {

    private final TemplateResponse templateResponse;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    public AuthService(TemplateResponse templateResponse, UserRepository userRepository, PasswordEncoder encoder){
        this.templateResponse = templateResponse;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public ResponseEntity<Map> register(User user){
        ResponseEntity<Map> response;
        try{
            response = new ResponseEntity<>(templateResponse.success(null), HttpStatus.OK);
        }catch (Exception e){
            response = new ResponseEntity<>(templateResponse.error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    public ResponseEntity<Map> login(User user){
        ResponseEntity<Map> response;
        try{
            User checkUser = userRepository.findByUsername(user.getUsername());
            if (checkUser == null) {
                return new ResponseEntity<>(templateResponse.error("Login credential don't match an account in our system"), HttpStatus.NOT_FOUND);
            }
            if (!(encoder.matches(user.getPassword(), checkUser.getPassword()))) {
                return new ResponseEntity<>(templateResponse.error("Login credential don't match an account in our system"), HttpStatus.NOT_FOUND);
            }
            return getToken(user.getEmail(), user.getPassword());
        }catch (Exception e){
            response = new ResponseEntity<>(templateResponse.error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    private ResponseEntity<Map> getToken(String email, String password) {
        logger.info("get Token");
        return null;
    }
}
