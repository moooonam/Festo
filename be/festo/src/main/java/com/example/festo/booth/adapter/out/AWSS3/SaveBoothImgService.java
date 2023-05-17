package com.example.festo.booth.adapter.out.AWSS3;

import com.example.festo.booth.application.port.out.SaveImgPort;
import com.example.festo.common.S3.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class SaveBoothImgService implements SaveImgPort {
    private final S3Uploader s3Uploader;
    @Value("${cloud.aws.s3.dir}")
    public String absolutePath;

    public String boothFolder(Long boothId) {
        StringBuilder saveFolderBuilder = new StringBuilder();
        saveFolderBuilder.append(absolutePath).append("/booth/").append(boothId.toString());
        File folder = new File(saveFolderBuilder.toString());
        if(!folder.exists()){//존재x
            folder.mkdir();
        }
        return saveFolderBuilder.toString();
    }


    @Override
    public String saveBoothImg(MultipartFile file, Long boothId) {
        String savedPath = boothFolder(boothId);
        String resultPath = savedPath + "/" + file.getOriginalFilename();
        String s3url;
        try {
           s3url  = s3Uploader.fileUpload(file,resultPath);
        } catch (IOException e) {
            log.info("s3업로드 중 문제 발생");
            throw new RuntimeException(e);
        }

        return s3url;
    }
}
