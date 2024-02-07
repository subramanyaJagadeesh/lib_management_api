package com.library_management.api.service.impl;

import com.library_management.api.exception.NotAuthorizedException;
import com.library_management.api.filter.JwtTokenFilter;
import com.library_management.api.model.User;
import com.library_management.api.repository.UserRepository;
import com.library_management.api.service.AuthService;
import com.library_management.api.service.UserService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    JwtTokenFilter jwtTokenFilter;

    public String login(String email, String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = userRepository.getUserByEmail(email);

        if(encoder.matches(password, user.getPassword())){
            return jwtTokenFilter.generateToken(user);
        } else {
            throw new NotAuthorizedException("User email or password is incorrect");
        }
    }

    public void signup(User user){
        userService.createUser(user.getFirstName(), user.getLastName(), user.getPhone(), user.getEmail(), 1, user.getPassword());
    }
}
