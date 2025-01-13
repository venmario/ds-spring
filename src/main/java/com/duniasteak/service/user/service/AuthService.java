package com.duniasteak.service.user.service;

import com.duniasteak.service.request.auth.LoginRequest;
import com.duniasteak.service.user.model.Fcm;
import com.duniasteak.service.user.model.User;
import com.duniasteak.service.user.repo.FcmRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthService {

    private final TemplateResponse templateResponse;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final FcmRepository fcmRepository;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    public AuthService(TemplateResponse templateResponse, UserRepository userRepository, PasswordEncoder encoder, JwtUtil jwtUtil, FcmRepository fcmRepository) {
        this.templateResponse = templateResponse;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
        this.fcmRepository = fcmRepository;
    }

    public ResponseEntity<Map> register(User user) {
        ResponseEntity<Map> response;
        try {
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);

            Map<String, Object> registerResponse = new HashMap<>();
            registerResponse.put("success", true);
            registerResponse.put("message", "User created successfully");
            registerResponse.put("code", HttpStatus.OK.value());
            response = new ResponseEntity<>(registerResponse, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response = new ResponseEntity<>(templateResponse.error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    @Transactional
    public ResponseEntity<Map> login(LoginRequest req) {
        ResponseEntity<Map> response;
        try {
            User user = userRepository.findByUsername(req.getUsername());
            if (user == null) {
                return new ResponseEntity<>(templateResponse.error("Login credentials are invalid."), HttpStatus.UNAUTHORIZED);
            }
            if (!(encoder.matches(req.getPassword(), user.getPassword()))) {
                return new ResponseEntity<>(templateResponse.error("Login credentials are invalid."), HttpStatus.UNAUTHORIZED);
            }

            boolean updatedFcmToken = false;
            if (req.getOldFcmToken().equals(req.getCurrentFcmToken())) {
                List<Fcm> userFcmTokens = user.getFcms();
                boolean found = false;
                for (Fcm userFcmToken : userFcmTokens) {
                    if (userFcmToken.getFcmToken().equals(req.getCurrentFcmToken())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
//                    Fcm::create(['fcm_token' => $currentFcmToken, 'user_id' => $user->id]);
//                    $updatedFcmToken = true;
                    Fcm fcm = new Fcm();
                    fcm.setFcmToken(req.getCurrentFcmToken());
                    fcm.setUser(user);
                    fcmRepository.save(fcm);
                    updatedFcmToken = true;
                }
            }else{
                fcmRepository.findById(req.getOldFcmToken()).ifPresent(fcmRepository::delete);
                Fcm fcm = new Fcm();
                fcm.setFcmToken(req.getCurrentFcmToken());
                fcm.setUser(user);
                fcmRepository.save(fcm);
                updatedFcmToken = true;
            }

            Map<String, Object> loginResponse = new HashMap<>();
            loginResponse.put("success", true);
            loginResponse.put("token", jwtUtil.generateToken(user.getUsername()));
            loginResponse.put("updatedFcmToken", updatedFcmToken);
            loginResponse.put("code", HttpStatus.OK.value());

            response = new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            response = new ResponseEntity<>(templateResponse.error(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }
}
