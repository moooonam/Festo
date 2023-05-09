package com.example.festo.festival.adapter.in.web;

import com.example.festo.festival.adapter.in.web.model.FestivalRequest;
import com.example.festo.festival.adapter.in.web.model.FestivalResponse;
import com.example.festo.festival.application.port.in.GetFestivalIdUseCase;
import com.example.festo.festival.application.port.in.GetFestivalsUseCase;
import com.example.festo.festival.application.port.in.RegisterFestivalCommand;
import com.example.festo.festival.application.port.in.RegisterFestivalUseCase;
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
public class FestivalController {

    private final RegisterFestivalUseCase registerFestivalUseCase;
    private final GetFestivalsUseCase getFestivalsUseCase;
    private final GetFestivalIdUseCase getFestivalIdUseCase;

    @PostMapping("festivals")
    public ResponseEntity<Long> createFestival(@RequestPart("request") FestivalRequest request, @RequestPart("festivalImg") MultipartFile festivalImg ){
        log.info("페스티벌 등록 컨트롤러 시작");
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        RegisterFestivalCommand command = RegisterFestivalCommand.builder()
                .festivalName(request.getFestivalName())
                .location(request.getLocation())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .img(festivalImg)
                .managerId(Long.parseLong(user.getUsername()))
                .build();

        Long festivalId=registerFestivalUseCase.registerFestival(command);

        return new ResponseEntity<Long>(festivalId, HttpStatus.OK);
    }

    @GetMapping("festivals")
    public ResponseEntity<List<FestivalResponse.MainPage>> getFestivals(){
        log.info("메인 페이지 페스티벌 조회 컨트롤러 시작");
        List<FestivalResponse.MainPage> festivalList = getFestivalsUseCase.getFestivalByMain();
        return new ResponseEntity<List<FestivalResponse.MainPage>>(festivalList, HttpStatus.OK);
    }

    @GetMapping("festivals/search")
    public ResponseEntity<List<FestivalResponse.Search>> getFestivalsBySearch(@RequestParam("keyword")String keyword){
        log.info("페스티벌 검색 조회 컨트롤러 시작");
        List<FestivalResponse.Search> festivalList = getFestivalsUseCase.getFestivalBySearch(keyword);
        return new ResponseEntity<List<FestivalResponse.Search>>(festivalList, HttpStatus.OK);
    }

    @GetMapping("festivals/invitation")
    public ResponseEntity<Long> getFestivalIdByInviteCode(@RequestParam("inviteCode")String inviteCode){
        log.info("페스티벌 초대코드 조회 컨트롤러 시작");
        Long festivalId = getFestivalIdUseCase.getFestivalIdByInviteCode(inviteCode);
        return new ResponseEntity<Long>(festivalId, HttpStatus.OK);
    }
}
