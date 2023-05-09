package com.example.festo.festival.application.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface SaveImgPort {
    String saveFestivalImg(MultipartFile file,Long festivalId);
}
