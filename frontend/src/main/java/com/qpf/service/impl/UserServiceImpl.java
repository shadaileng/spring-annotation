package com.qpf.service.impl;

import com.qpf.bean.User;
import com.qpf.bean.dto.Page;
import com.qpf.dao.UserMapper;
import com.qpf.service.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements ModelService<User> {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserMapper userMapper;
    public User query(User user) {
        return userMapper.selectUserCondition(user);
    }

    @Override
    public User queryById(Integer id) {
        return userMapper.queryUserById(id);
    }

    @Override
    public int deleteByIds(List<Integer> ids) {
        int delete = userMapper.deleteUserByIds(ids);
        return delete;
    }

    @Override
    public boolean assign(Integer id, List<Integer> ids) {
        boolean result = false;
        try {
            int delete = userMapper.deleteRolesById(id);
            logger.info("删除角色: {}", delete);
            int add = userMapper.addRolesById(id, ids);
            logger.info("添加角色: {}", add);
            result = true;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return result;
    }

    @Override
    public List<User> queryCondition(User user) {
        return null;
    }

    @Override
    public List<User> queryAll() {
        return null;
    }

    @Override
    public List queryManyById(Integer id) {
        List<Integer> roles = userMapper.queryRolesIdById(id);
        return roles;
    }

    public Page<User> queryPage(Map<String, Object> map) {
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

    public int add(User user) {
        user.setPassword("123456");
        int id = -1;
        try {
            id = userMapper.addUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return id;
    }

    @Override
    public int edit(User user) {
        int count = userMapper.editUser(user);
        return count;
    }
}
