package com.vic.tasker.Controller;

import com.vic.tasker.Dto.ImageResponse;
import com.vic.tasker.Service.GoogleUploadInterface;
import com.vic.tasker.Service.GoogleUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class GoogleUploadController {

    @Autowired
    private GoogleUploadInterface uploadInterface;

//    @PostMapping("/googleUpload")
//    ResponseEntity<ImageResponse> uploadImageToGoogleDrive(@RequestParam("image") MultipartFile file) throws IOException {
//        File tempFile = File.createTempFile("temp", null);
//        file.transferTo(tempFile);
//        ImageResponse imageResponse = uploadInterface.uploadImageToDrive(tempFile);
//
//        return new ResponseEntity<>(imageResponse, HttpStatus.OK);
//    }

    @PostMapping("/googleUpload")
    public ResponseEntity<ImageResponse> uploadImageToGoogleDrive(@RequestParam("image") MultipartFile file) throws IOException {
        File tempFile = File.createTempFile("temp", null);
        file.transferTo(tempFile);
        System.out.println("File received: " + tempFile.getAbsolutePath());
        ImageResponse imageResponse = uploadInterface.uploadImageToDrive(tempFile);

        return new ResponseEntity<>(imageResponse, HttpStatus.OK);
    }


    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        return file;
    }

}
