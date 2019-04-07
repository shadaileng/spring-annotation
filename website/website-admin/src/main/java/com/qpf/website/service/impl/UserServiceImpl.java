package com.qpf.website.service.impl;

import com.qpf.website.absract.AbstractServiceImpl;
import com.qpf.website.dao.UserDao;
import com.qpf.website.entity.User;
import com.qpf.website.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
@Transactional
@Service("userService")
public class UserServiceImpl extends AbstractServiceImpl<User, UserDao> implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public User login(String email, String password) {
        logger.debug("call login | email: {}, password: {}", email, password);
        User user = null;

        user = dao.getUserByEmail(email);

        if (user != null && !user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
            user = null;
        }

        return user;
    }
    public boolean emailExists(String email) {
        boolean result = false;
        try {
            User user = dao.getUserByEmail(email);
            if (user != null) {
                result = true;
            }
        } catch (Exception e) {
            result = false;
        }
        return result;
    }
}
