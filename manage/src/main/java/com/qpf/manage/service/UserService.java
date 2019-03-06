package com.qpf.manage.service;

import com.qpf.manage.entity.User;

public interface UserService {
    User login(String email, String password);
}
