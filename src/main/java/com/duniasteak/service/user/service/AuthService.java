package com.duniasteak.service.user.service;

import com.duniasteak.service.user.model.User;
import com.duniasteak.service.user.repo.UserRepository;
import com.duniasteak.service.util.JwtUtil;
import com.duniasteak.service.util.TemplateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final TemplateResponse templateResponse;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    public AuthService(TemplateResponse templateResponse, UserRepository userRepository, PasswordEncoder encoder, JwtUtil jwtUtil) {
        this.templateResponse = templateResponse;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<Map> register(User user) {
        ResponseEntity<Map> response;
        try {
            userRepository.save(user);

            Map<String, Object> registerResponse = new HashMap<>();
            registerResponse.put("success", true);
            registerResponse.put("message", "User created successfully");
            registerResponse.put("code", HttpStatus.OK.value());
            response = new ResponseEntity<>(templateResponse.success(null), HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response = new ResponseEntity<>(templateResponse.error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    public ResponseEntity<Map> login(User user) {
        ResponseEntity<Map> response;
        try {
            User checkUser = userRepository.findByUsername(user.getUsername());
            if (checkUser == null) {
                return new ResponseEntity<>(templateResponse.error("Login credentials are invalid."), HttpStatus.UNAUTHORIZED);
            }
            if (!(encoder.matches(user.getPassword(), checkUser.getPassword()))) {
                return new ResponseEntity<>(templateResponse.error("Login credentials are invalid."), HttpStatus.UNAUTHORIZED);
            }
            response = new ResponseEntity<>(templateResponse.success(jwtUtil.generateToken(user.getUsername())), HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response = new ResponseEntity<>(templateResponse.error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
