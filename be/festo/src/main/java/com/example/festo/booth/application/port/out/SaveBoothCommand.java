package com.example.festo.booth.application.port.out;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
public class SaveBoothCommand {
    private String boothName;
    private String location;
    private String description;
    private LocalTime openTime;
    private LocalTime closeTime;
    private String imageUrl;


    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
