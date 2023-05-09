package com.example.festo.member.adapter.in.web;

import com.example.festo.global.auth.CustomUserDetails;
import com.example.festo.member.adapter.in.web.model.MemberInfoResponse;
import com.example.festo.member.application.port.in.MemberInfoUseCase;
import com.example.festo.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberInfoUseCase memberInfoUseCase;

    @GetMapping("/api/v1/members/me")
    public ResponseEntity<MemberInfoResponse> loadMemberInfo(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();

        MemberInfoResponse memberInfoResponse = memberInfoUseCase.loadMemberInfo(member.getId());

        return ResponseEntity.ok(memberInfoResponse);
    }
}
