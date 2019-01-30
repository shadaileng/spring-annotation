package com.qpf.service;

import com.qpf.bean.User;
import com.qpf.bean.dto.Page;

import java.util.Map;

public interface UserService {
    Page<User> queryUserPage(Map<String, Object> map);
    int addUser(User user);

    int editUser(User user);

    User queryUser(User user);
}
