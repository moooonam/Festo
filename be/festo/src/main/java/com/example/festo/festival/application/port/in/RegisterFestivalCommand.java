package com.example.festo.festival.application.port.in;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Builder
@Getter
public class RegisterFestivalCommand {
    private String festivalName;
    private String location;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private MultipartFile img;
    private Long managerId;
}
