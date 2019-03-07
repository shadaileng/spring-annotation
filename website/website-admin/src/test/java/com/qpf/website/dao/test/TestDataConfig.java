package com.qpf.website.dao.test;

import com.qpf.website.dao.UserDao;
import com.qpf.website.entity.User;
import com.qpf.website.web.config.DataConfig;
import com.qpf.website.web.config.RootConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;

// Web环境
@WebAppConfiguration
@ContextConfiguration(classes={RootConfig.class, DataConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDataConfig {
    @Autowired
    private UserDao userDao;
    @Test
    public void testUserDao() {
        User user = userDao.getUserByEmailAndPassword("qpf@qq.com", "qpf");
        System.out.println(user);
        user = userDao.getUserByEmail("qpf@qq.com");
        System.out.println(user);
        List<User> users = userDao.getAll();
        System.out.println(users);
    }
}
