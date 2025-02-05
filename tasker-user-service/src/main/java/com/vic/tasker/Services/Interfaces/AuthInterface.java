package com.vic.tasker.Services.Interfaces;

import com.vic.tasker.Dtos.AuthResponse;
import com.vic.tasker.Dtos.LoginRequest;
import com.vic.tasker.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface AuthInterface {

    AuthResponse createUser(User user);

    AuthResponse signUserIn(LoginRequest loginRequest);


}
