package com.vic.tasker.Services.Interfaces;

import com.vic.tasker.Dtos.ImageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@FeignClient(name = "TASKER-GOOGLEUPLOAD-SERVICE",url = "http://localhost:5008")
public interface GoogleUploadInterface {


        @GetMapping("/api/googleUpload")
        public ImageResponse uploadImageToGoogleDrive(@RequestBody MultipartFile file);

}
