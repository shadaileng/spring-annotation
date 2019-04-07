package com.qpf.website.service;

import com.qpf.website.entity.User;

public interface UserService {
    User login(String username, String password);
}
