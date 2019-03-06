package com.qpf.manage.dao.impl;

import com.qpf.manage.dao.UserDao;
import com.qpf.manage.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository("userDao")
public class UserDaoImpl implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
    @Override
    public User getUserByEmailAndPassword(String email, String password) {
        logger.debug("call getUserByEmailAndPassword() | email: {}, password: {}", email, password);
        User user = null;

        if (StringUtils.equals("admin@qq.com", email)) {
            if (StringUtils.equals("admin", password)) {
                user = new User();
                user.setEmail(email);
                user.setUsername("shadaileng");
                user.setPassword("******");

                logger.info("get User: {}", user);
            }
        } else {
            logger.warn("Can't get user");
        }

        return user;
    }

    @Override
    public List<User> getAll() {
        return null;
    }
}
