package com.qpf.service;

import com.qpf.bean.User;
import com.qpf.bean.dto.Page;

import java.util.Map;

public interface UserService {
    Page<User> queryUserPage(Map<String, Object> map);
    int addUser(User user) throws Exception;

    int editUser(User user) throws Exception;

    User queryUser(User user);

    User queryUserById(Integer id);
}
