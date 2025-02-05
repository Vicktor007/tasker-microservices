package com.vic.tasker.Controllers;

import com.vic.tasker.Dtos.ApiResponse;
import com.vic.tasker.Dtos.AuthResponse;
import com.vic.tasker.Dtos.LoginRequest;
import com.vic.tasker.Services.Interfaces.AuthInterface;
import com.vic.tasker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class authController {

    @Autowired
    AuthInterface authInterface;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUser(
            @RequestBody User user
    ) throws IOException {
//        File file1 = convertMultipartFileToFile(file);
        AuthResponse authResponse = authInterface.createUser(user);
        return ResponseEntity.ok().body(authResponse);

    }


    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signUserIn(
            @RequestBody LoginRequest loginRequest
            ) {
        AuthResponse authResponse = authInterface.signUserIn(loginRequest);

        return ResponseEntity.ok().body(authResponse);
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse> userHomeController(){
        ApiResponse res = new ApiResponse("Welcome To Task Management User Service",true);
        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }



}
