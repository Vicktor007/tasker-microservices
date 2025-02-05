package com.vic.tasker.Services.Implementations;

import com.vic.tasker.Dtos.ImageResponse;
import com.vic.tasker.Services.Interfaces.GoogleUploadInterface;
import com.vic.tasker.Services.Interfaces.UserInterface;
import com.vic.tasker.Utils.JwtUtils;
import com.vic.tasker.exceptions.UserException;
import com.vic.tasker.model.User;
import com.vic.tasker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserInterface {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GoogleUploadInterface googleUploadInterface;

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String email = JwtUtils.getEmailFromJwtToken(jwt);

        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new UserException("user does not exist");
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws UserException {
        User user = userRepository.findByEmail(email);

        if(user != null){
            return user;
        }
        throw new UserException("User does not exist");
    }

    @Override
    public User findUserById(String userId) throws UserException {
        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isEmpty()){
            throw new UserException("user not found");
        }
        return optionalUser.get();
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUserProfile(String userId, User req) throws UserException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException("User doesnt exist"));



        if(req.getFullName()!=null){
            user.setFullName(req.getFullName());
        }
        if(req.getProfilePhoto()!=null){
            user.setProfilePhoto(req.getProfilePhoto());
        }
        if (req.getPhotoId()!=null){
            user.setPhotoId(req.getPhotoId());
        }
       if(req.getMobileNumber()!=null){
           user.setMobileNumber(req.getMobileNumber());
       }

        return userRepository.save(user);
    }
}
