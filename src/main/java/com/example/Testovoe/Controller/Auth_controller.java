package com.example.Testovoe.Controller;


import com.example.Testovoe.Payload.Request.Login_Request;
import com.example.Testovoe.Payload.Request.Signup_Request;
import com.example.Testovoe.Payload.Response.JWT_Token_Success_Response;
import com.example.Testovoe.Security.JWTTokenProvider;
import com.example.Testovoe.Security.SecurityConstants;
import com.example.Testovoe.Service.User_service;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@RestController
@RequestMapping("/api/auth")
public class Auth_controller {
    @Autowired
    private User_service userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    private final Lock transferLock = new ReentrantLock();

    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody Login_Request loginRequest, BindingResult bindingResult) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWT_Token_Success_Response(true, jwt));
    }


    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody Signup_Request signupRequest, BindingResult bindingResult) throws Exception {


        userService.createUser(signupRequest);
        return ResponseEntity.ok("Пользователь успешно зарегестрирован");
    }
}
