package com.qpf.service.impl;

import com.qpf.bean.User;
import com.qpf.bean.dto.Page;
import com.qpf.dao.UserMapper;
import com.qpf.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    public User queryUser(User user) {
        return userMapper.selectUserCondition(user);
    }

    public Page<User> queryUserPage(Map<String, Object> map) {
        int count = userMapper.queryCount(map);
        List<User> users = userMapper.queryUserPage(map);

        Page<User> page = new Page<>();
        page.setData(users);
        page.setIndex((Integer) map.get("index") / (Integer) map.get("size") + 1);
        page.setPageSize((Integer) map.get("size"));
        page.setPageCount((count - 1) / (Integer) map.get("size") + 1);
        page.setTotal(count);

        return page;
    }

    public int addUser(User user) {
        user.setPassword("123456");
        int id = userMapper.addUser(user);
        return id;
    }

    @Override
    public int editUser(User user) {
        int count = userMapper.editUser(user);
        return count;
    }
}
