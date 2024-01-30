package com.library_management.api.repository;

import com.library_management.api.model.User;

import java.util.List;

public interface UserRepository {
    List<User> getAllUsers(Boolean type);

    User getUser(Long id);

    User getUserByEmail(String email);

    void createUser(String fName, String lName, String phone, String email, Integer type, String password);

    void deleteUser(Long userId);
}
