package com.example.festo.booth.adapter.in.web;

import com.example.festo.booth.adapter.in.web.model.BoothRequest;
import com.example.festo.booth.adapter.in.web.model.FiestaResponse;
import com.example.festo.booth.application.port.in.ChangeBoothStatusUseCase;
import com.example.festo.booth.application.port.in.GetFiestaListUseCase;
import com.example.festo.booth.application.port.in.RegisterBoothCommand;
import com.example.festo.booth.application.port.in.RegisterBoothUseCase;
import com.example.festo.booth.domain.BoothStatus;
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
    private final ChangeBoothStatusUseCase changeBoothStatusUseCase;
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

    @PatchMapping("booths/{booth_id}/status")
    public ResponseEntity<?> changeBoothStatus(@PathVariable("booth_id")Long boothId, @RequestBody String status){
        log.info("부스 상태 변경 컨트롤러 시작");

        boolean resultResponse = changeBoothStatusUseCase.changeBoothStatus(status,boothId);

        if(resultResponse){
            return new ResponseEntity<>("변경이 완료돠었습니다.", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(status + "에서 " + status+"로 변경을 하지 못합니다.", HttpStatus.BAD_REQUEST);
        }

    }

//    @GetMapping("booths/{booth_id}")
//    public ResponseEntity<?> getBoothDetail(@PathVariable("booth_id")Long boothId){
//        log.info("부스 상세 정보 조회 컨트롤러 시작");
//
//        return new ResponseEntity<>("변경이 완료돠었습니다.", HttpStatus.OK);
//    }


}
