package com.vic.tasker.Services;

import com.vic.tasker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import com.vic.tasker.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerUserdetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if(user==null) {

            throw new UsernameNotFoundException("user not found with email  - "+    email);
        }

        List<GrantedAuthority> authorities=new ArrayList<>();

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),user.getPassword(),authorities);
    }
    }

