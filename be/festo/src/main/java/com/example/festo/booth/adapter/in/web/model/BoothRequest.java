package com.example.festo.booth.adapter.in.web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class BoothRequest {
    @NotBlank(message = "이름을 적어주세요.")
    private String boothName;
    @NotBlank(message = "위치를 적어주세요.")
    private String location;
    @NotBlank(message = "설명을 적어주세요.")
    private String description;
    @NotNull(message = "영업 시작 시간을 작성해주세요.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime openTime;
    @NotNull(message = "영업 종료 시간을 작성해주세요.")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime closeTime;
}
