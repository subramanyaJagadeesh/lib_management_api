package com.library_management.api.service;

import com.library_management.api.model.User;

public interface AuthService {

    public String signup(User user);
    public String login(String email, String password);
}
