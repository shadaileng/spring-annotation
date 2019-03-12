package com.qpf.website.service.impl;

import com.qpf.website.commons.dto.BaseResult;
import com.qpf.website.commons.utils.RegexpUtils;
import com.qpf.website.dao.UserDao;
import com.qpf.website.entity.User;
import com.qpf.website.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;
    @Override
    public User login(String email, String password) {
        logger.debug("call login | email: {}, password: {}", email, password);
        User user = null;

        user = userDao.getUserByEmail(email);

        if (user != null && !user.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes()))) {
            user = null;
        }

//        user = userDao.getUserByEmailAndPassword(email, password);

        return user;
    }

    public List<User> list() {
        return userDao.getAll();
    }

    @Override
    public BaseResult delete(List<String> ids) {
        BaseResult result = BaseResult.success("删除成功");
        try {
            if (userDao.deleteUserById(ids) <= 0) {
                result = BaseResult.failed("删除失败");
            }
        } catch (Exception e) {
            logger.error("user | delete: {}", e.getMessage());
            result = BaseResult.failed(String.format("删除失败: %s", e.getMessage()));
        }
        return result;
    }

    @Override
    public User getUserById(int id) {
        return userDao.selectUserById(id);
    }

    public BaseResult save(User user) {

        BaseResult result = checkUser(user);
        if (result.getCode() == BaseResult.STATUS_SUCCESS) {
            try {
                Date now = new Date();
                user.setUpdated(now);
                // 更新
                if (user.getId() != null) {
                    if (userDao.update(user) <= 0) {
                        result = BaseResult.failed("更新用户失败");
                    }
                }
                // 新增
                else {
                    user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
                    user.setCreated(now);
                    if (userDao.insert(user) <= 0) {
                        result = BaseResult.failed("新增用户失败");
                    }
                }
            } catch (Exception e) {
                logger.error("user | save: {}", e.getMessage());
                result = BaseResult.failed(String.format("操作失败: %s", e.getMessage()));
            }
        }

        return result;
    }

    private BaseResult checkUser(User user) {
        BaseResult result = BaseResult.success();

        if (StringUtils.isBlank(user.getUsername())) {
            result = BaseResult.failed("姓名不能为空");
        }
        if (StringUtils.isBlank(user.getEmail())) {
            result = BaseResult.failed("邮箱不能为空");
        } else if(!RegexpUtils.checkEmail(user.getEmail())) {
            result = BaseResult.failed("邮箱格式不正确");
        } else if (user.getId() == null && userDao.getUserByEmail(user.getEmail()) != null) {
            result = BaseResult.failed("邮箱已注册");
        }
        if (StringUtils.isBlank(user.getPhone())) {
            result = BaseResult.failed("手机号码不能为空");
        } else if(!RegexpUtils.checkPhone(user.getPhone())) {
            result = BaseResult.failed("手机号码格式不正确");
        }

        return result;
    }
}
