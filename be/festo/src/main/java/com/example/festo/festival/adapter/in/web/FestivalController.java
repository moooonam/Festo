package com.example.festo.festival.adapter.in.web;

import com.example.festo.festival.adapter.in.web.model.FestivalRequest;
import com.example.festo.festival.application.port.in.RegisterFestivalCommand;
import com.example.festo.festival.application.port.in.RegisterFestivalUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Slf4j
public class FestivalController {

    private final RegisterFestivalUseCase registerFestivalUseCase;

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
}
