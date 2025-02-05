package com.vic.tasker.Services.Interfaces;

import com.vic.tasker.exceptions.UserException;
import com.vic.tasker.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserInterface {

    User findUserProfileByJwt(String jwt) throws UserException;

    User findUserByEmail(String email) throws UserException;

    User findUserById(String userId) throws UserException;

    List<User>findAllUsers();

    User updateUserProfile(String userId, User req) throws UserException;
}
