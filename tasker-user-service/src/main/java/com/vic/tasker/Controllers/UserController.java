package com.vic.tasker.Controllers;

import com.vic.tasker.Services.Interfaces.UserInterface;
import com.vic.tasker.exceptions.UserException;
import com.vic.tasker.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserInterface userInterface;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(
            @RequestHeader("Authorization") String jwt
    ) throws UserException {
        User user = userInterface.findUserProfileByJwt(jwt);
        user.setPassword(null);
        return new ResponseEntity<>(user, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> findUerById(
            @PathVariable String userId,
            @RequestHeader("Authorization") String jwt
    ) throws UserException {
        User user = userInterface.findUserById(userId);
        user.setPassword(null);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> findAllUsers(
            @RequestHeader("Authorization") String jwt
    ){
        List<User> users = userInterface.findAllUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<User> updateUserProfile(
           @RequestBody User req,
            @RequestHeader("Authorization") String jwt

    ) throws IOException, UserException {
//
        User userId = userInterface.findUserProfileByJwt(jwt);
        User user = userInterface.updateUserProfile(userId.getId(),req);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        return file;
    }
}
