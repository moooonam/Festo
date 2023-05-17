package com.example.festo.product.adapter.out.AWSS3;

import com.example.festo.common.S3.S3Uploader;
import com.example.festo.product.application.port.out.SaveImgPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class SaveProductImgService implements SaveImgPort {

    private final S3Uploader s3Uploader;
    @Value("${cloud.aws.s3.dir}")
    public String absolutePath;

    public String productFolder(Long festivalId) {
        StringBuilder saveFolderBuilder = new StringBuilder();
        saveFolderBuilder.append(absolutePath)
                         .append("/product/")
                         .append(festivalId.toString());
        File folder = new File(saveFolderBuilder.toString());
        if (!folder.exists()) {//존재x
            folder.mkdir();
        }
        return saveFolderBuilder.toString();
    }


    @Override
    public String saveProductImg(MultipartFile file, Long festivalId) {
        String savedPath = productFolder(festivalId);
        String resultPath = savedPath + "/" + file.getOriginalFilename();
        String s3url;
        try {
            s3url = s3Uploader.fileUpload(file, resultPath);
        } catch (IOException e) {
            log.info("s3업로드 중 문제 발생");
            throw new RuntimeException(e);
        }

        return s3url;
    }
}
