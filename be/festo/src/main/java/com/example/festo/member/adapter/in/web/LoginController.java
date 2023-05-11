package com.example.festo.member.adapter.in.web;

import com.example.festo.member.adapter.in.web.model.LoginRequest;
import com.example.festo.member.adapter.in.web.model.LoginResponse;
import com.example.festo.member.application.port.in.LoginUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginUseCase loginUseCase;

    @PostMapping("/api/v1/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        LoginResponse loginResponse = loginUseCase.login(loginRequest.getAuthId(), loginRequest.getNickname(), loginRequest.getProfileImageUrl());

        return ResponseEntity.ok(loginResponse);
    }
}
