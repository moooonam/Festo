package com.example.festo.festival.adapter.in.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class FestivalRequest {
    @NotBlank(message = "이름을 적어주세요.")
    private String festivalName;
    @NotBlank(message = "위치를 적어주세요.")
    private String location;
    @NotBlank(message = "설명을 적어주세요.")
    private String description;
    @NotNull(message = "시작 날짜를 지정해주세요.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    private LocalDate startDate;
    @NotNull(message = "종료 날짜를 지정해주세요.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
    private LocalDate endDate;
}
