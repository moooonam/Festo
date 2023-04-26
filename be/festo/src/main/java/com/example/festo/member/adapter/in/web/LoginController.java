package com.example.festo.member.adapter.in.web;

import com.example.festo.member.application.LoginService;
import com.example.festo.member.application.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/api/v1/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = loginService.login(loginRequest.getAuthId(), loginRequest.getNickname(), loginRequest.getProfileImageUrl());

        return ResponseEntity.ok(loginResponse);
    }
}
