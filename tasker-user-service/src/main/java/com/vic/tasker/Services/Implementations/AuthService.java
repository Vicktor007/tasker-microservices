package com.vic.tasker.Services.Implementations;

import com.vic.tasker.Dtos.AuthResponse;
import com.vic.tasker.Dtos.ImageResponse;
import com.vic.tasker.Dtos.LoginRequest;
import com.vic.tasker.Services.CustomerUserdetailsService;
import com.vic.tasker.Services.GoogleUploadService;
import com.vic.tasker.Services.Interfaces.AuthInterface;
import com.vic.tasker.Services.Interfaces.GoogleUploadInterface;
import com.vic.tasker.Utils.JwtUtils;
import com.vic.tasker.exceptions.UserException;
import com.vic.tasker.model.User;
import com.vic.tasker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class AuthService implements AuthInterface {

    @Autowired
    private CustomerUserdetailsService customerUserdetailsService;

    @Autowired
    GoogleUploadService uploadService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    GoogleUploadInterface googleUploadInterface;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public AuthResponse createUser(User user) {
        AuthResponse authResponse = new AuthResponse();
        try {
            User existingUser = userRepository.findByEmail(user.getEmail());
            if (existingUser != null){
                throw new UserException("User already exists!");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));

//            ImageResponse uploadedFile = uploadService.uploadImageToDrive(file);
//            ImageResponse uploadedFile = googleUploadInterface.uploadImageToGoogleDrive(file);
//            user.setProfilePhoto(uploadedFile.getUrl());
//            user.setPhotoId(uploadedFile.getId());

            User savedUser = userRepository.save(user);

            Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);


            authResponse.setMessage("User registered successfully");
            authResponse.setStatus(201);
        } catch (UserException e) {
            throw new RuntimeException(e);
        }
        return authResponse;
    }

    @Override
    public AuthResponse signUserIn(LoginRequest loginRequest) {
        AuthResponse authResponse = new AuthResponse();
        try {
            System.out.println(loginRequest.getEmail());
            var user = userRepository.findByEmail(loginRequest.getEmail());

            if(user == null) {
                throw new UserException("User not found!");
            }

            Authentication authentication = authenticate(loginRequest.getEmail(),loginRequest.getPassword());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = JwtUtils.generateToken(authentication);

            authResponse.setMessage("Logged in successfully");
            authResponse.setJwt(token);
            authResponse.setStatus(200);
        } catch (UserException e) {
            throw new RuntimeException(e);

        }
        return authResponse;
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customerUserdetailsService.loadUserByUsername(username);

        System.out.println("sign in userDetails - " + userDetails);

        if (userDetails == null) {
            System.out.println("sign in userDetails - null " + userDetails);
            throw new BadCredentialsException("Invalid username or password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            System.out.println("sign in userDetails - password not match " + userDetails);
            throw new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
