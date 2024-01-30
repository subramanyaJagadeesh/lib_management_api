package com.library_management.api.service;

import com.library_management.api.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers(Boolean type);
    User getUser(Long id);
    void createUser(String fName, String lName, String phone, String email, Integer type, String password);
    void deleteUser(Long userId);
}
