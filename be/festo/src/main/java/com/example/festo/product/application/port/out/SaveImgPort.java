package com.example.festo.product.application.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface SaveImgPort {

    String saveProductImg(MultipartFile file, Long productId);
}
