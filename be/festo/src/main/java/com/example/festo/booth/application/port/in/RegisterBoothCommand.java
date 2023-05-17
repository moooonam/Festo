package com.example.festo.booth.application.port.in;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
public class RegisterBoothCommand {

    private String boothName;
    private String location;
    private String description;
    private LocalTime openTime;
    private LocalTime closeTime;
    private Long festivalId;
    private MultipartFile img;
    private Long ownerId;

}
