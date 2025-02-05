package com.vic.tasker.Services.Interfaces;

import com.vic.tasker.dtos.ImageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "TASKER-GOOGLEUPLOAD-SERVICE",url = "http://localhost:5008")
public interface GoogleUploadInterface {


        @PostMapping("/api/googleUpload")
        public ImageResponse uploadImageToGoogleDrive(@RequestParam(value = "file") MultipartFile file);
    
}
