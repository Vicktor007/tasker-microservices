package com.vic.tasker.Services.Interfaces;

import com.vic.tasker.dtos.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "TASKER-USER-SERVICE",url = "http://localhost:5001")
public interface UserInterface {
    @GetMapping("/api/users/profile")
    public UserDto getUserProfile(
            @RequestHeader("Authorization") String jwt
    );
}
