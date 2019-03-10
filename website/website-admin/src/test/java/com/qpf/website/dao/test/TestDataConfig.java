package com.qpf.website.dao.test;

import com.qpf.website.dao.UserDao;
import com.qpf.website.entity.User;
import com.qpf.website.web.config.DataConfig;
import com.qpf.website.web.config.RootConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

// Web环境
@WebAppConfiguration
@ContextConfiguration(classes={RootConfig.class, DataConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestDataConfig {
    private static final Logger logger = LoggerFactory.getLogger(TestDataConfig.class);
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

    @Test
    public void testInsert() {
        int insert = 0;
        try {
            User user = new User();
            user.setUsername("test");
            user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
            user.setEmail("test@qq.com");
            user.setPhone("13588888888");
            Date now = new Date();
            user.setCreated(now);
            user.setUpdated(now);
            insert = userDao.insert(user);
        } catch (Exception e) {
            logger.error("insert: {}, \n{}", insert, e.getMessage());
        }
        Assert.assertEquals(insert, 1);
    }

    @Test
    public void testUpdate() {
        int update = 0;
        try {
            User user = userDao.selectUserById(5);
            logger.info("user: {}", user);
            user.setUsername("tset1");
            user.setUpdated(new Date());
            update = userDao.update(user);
        } catch (Exception e) {
            logger.error("update: {}, \n{}", update, e.getMessage());
        }
        Assert.assertEquals(update, 1);
    }
}
