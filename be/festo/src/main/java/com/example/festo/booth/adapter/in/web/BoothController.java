package com.example.festo.booth.adapter.in.web;

import com.example.festo.booth.adapter.in.web.model.BoothRequest;
import com.example.festo.booth.adapter.in.web.model.FiestaResponse;
import com.example.festo.booth.application.port.in.GetFiestaListUseCase;
import com.example.festo.booth.application.port.in.RegisterBoothCommand;
import com.example.festo.booth.application.port.in.RegisterBoothUseCase;
import com.example.festo.festival.adapter.in.web.model.FestivalResponse;
import com.example.festo.festival.application.port.in.GetFestivalDetailUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BoothController {
    private final RegisterBoothUseCase registerBoothUseCase;
    private final GetFiestaListUseCase getFiestaListUseCase;
    @PostMapping("/festivals/{festival_id}/booths")
    public ResponseEntity<Long> createBooth(@PathVariable("festival_id") Long festivalId, @RequestPart("request") BoothRequest request, @RequestPart("boothImg") MultipartFile boothImg){
        log.info("부스 등록 컨트롤러 시작");
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

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

    @GetMapping("festivals/owner")
    public ResponseEntity<?> getFestivalsByOwner(){
        log.info("부스 운영자가 참여한 축제 조회 컨트롤러 시작");

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<FiestaResponse.Owner> festivalList = getFiestaListUseCase.getFiestaListByOwner(Long.parseLong(user.getUsername()));

        return new ResponseEntity<>(festivalList, HttpStatus.OK);
    }

}
