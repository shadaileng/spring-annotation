package com.qpf.website.service.impl;

import com.qpf.website.dao.UserDao;
import com.qpf.website.entity.User;
import com.qpf.website.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;
    @Override
    public User login(String email, String password) {
        logger.debug("call login | email: {}, password: {}", email, password);
        User user = null;

        user = userDao.getUserByEmailAndPassword(email, password);

        return user;
    }
}
