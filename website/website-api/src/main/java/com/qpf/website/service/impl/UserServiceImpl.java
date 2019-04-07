package com.qpf.website.service.impl;

import com.qpf.website.dao.UserDao;
import com.qpf.website.entity.User;
import com.qpf.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao dao;
    @Override
    public User login(String username, String password) {
        User user = dao.login(username);

        if (user != null) {
            password = DigestUtils.md5DigestAsHex(password.getBytes());
            if (password.equals(user.getPassword())) {
                return user;
            }
        }

        return user;
    }
}
