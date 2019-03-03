package com.qpf.manage.service.impl;

import com.qpf.manage.commons.utils.SpringContext;
import com.qpf.manage.dao.UserDao;
import com.qpf.manage.entity.User;
import com.qpf.manage.service.UserService;
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
    public User login(String email, String passworld) {
        logger.debug("call login | email: {}, password: {}", email, passworld);
        User user = null;

        user = userDao.getUserByEmailAndPassword(email, passworld);

        return user;
    }
}
