package com.library_management.api.service.impl;

import com.library_management.api.Model.User;
import com.library_management.api.repository.UserRepository;
import com.library_management.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(Boolean type){
        return userRepository.getAllUsers(type);
    }

    public User getUser(Long id){
        return userRepository.getUser(id);
    }

    public void createUser(String fName, String lName, String phone, String email, Integer type) {
        userRepository.createUser(fName, lName, phone, email, type);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteUser(userId);
    }
}
