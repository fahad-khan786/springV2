package com.example.demo.controller;

import com.example.demo.entity.UserInfo;
import com.example.demo.model.AuthRequest;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.security.JwtService;
//import com.example.demo.services.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class JwtController {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired private JwtService jwtService;
    @Autowired private UserInfoRepository repo;
    @Autowired private PasswordEncoder encoder;

    @PostMapping("/register")
    public String register(@RequestBody UserInfo user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repo.save(user);
        return "Registered Successfully!";
    }

    @PostMapping("/login")
    public String login(@RequestBody UserInfo user) {
//        authManager.authenticate(
//                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
//        );

        return jwtService.generateToken(user.getEmail());
    }


}
