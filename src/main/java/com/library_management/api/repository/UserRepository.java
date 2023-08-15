package com.library_management.api.repository;

import com.library_management.api.Model.User;

import java.util.List;

public interface UserRepository {
    List<User> getAllUsers(Boolean type);

    User getUser(Long id);

    void createUser(String fName, String lName, String phone, String email, Integer type);

    void deleteUser(Long userId);
}
