package com.qpf.website.service;

import com.qpf.website.commons.persistence.BaseService;
import com.qpf.website.entity.User;

public interface UserService extends BaseService<User> {
    User login(String email, String password);
    boolean emailExists(String email);
}
