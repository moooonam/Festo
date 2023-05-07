package com.example.festo.booth.adapter.in.web;

import com.example.festo.booth.adapter.in.web.model.BoothRequest;
import com.example.festo.booth.application.port.in.RegisterBoothCommand;
import com.example.festo.booth.application.port.in.RegisterBoothUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BoothController {
    private final RegisterBoothUseCase registerBoothUseCase;
    @PostMapping("/festivals/{festival_id}/booths")
    public ResponseEntity<Long> createBooth(@PathVariable("festival_id") Long festivalId, @RequestPart("request") BoothRequest request, @RequestPart("boothImg") MultipartFile boothImg){
        log.info("부스 등록 컨트롤러 시작");
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //User는 왜 안될까?

        log.info(user.getUsername() + " user 아이디 입니다.");
        RegisterBoothCommand command = RegisterBoothCommand.builder()
                .boothName(request.getBoothName())
                .location(request.getLocation())
                .description(request.getDescription())
                .openTime(request.getOpenTime())
                .closeTime(request.getCloseTime())
                .festivalId(festivalId)
                .img(boothImg)
                .ownerId(Long.parseLong(user.getUsername()))
                .build();

        Long boothId = registerBoothUseCase.registerBooth(command);

        return new ResponseEntity<Long>(boothId, HttpStatus.OK);
    }

}
