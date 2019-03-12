package com.qpf.website.service;

import com.qpf.website.commons.dto.BaseResult;
import com.qpf.website.entity.User;

import java.util.List;

public interface UserService {
    User login(String email, String password);

    BaseResult save(User user);

    List<User> list();

    BaseResult delete(List<String> ids);

    User getUserById(int id);
}
