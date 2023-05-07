package com.example.festo.booth.application.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface SaveImgPort {
    String saveBoothImg(MultipartFile file,Long boothId);
}
