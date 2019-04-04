package com.qpf.website.dao.test;

import com.qpf.website.dao.ContentCategoryDao;
import com.qpf.website.dao.ContentDao;
import com.qpf.website.dao.UserDao;
import com.qpf.website.entity.Content;
import com.qpf.website.entity.ContentCategory;
import com.qpf.website.entity.User;
import com.qpf.website.web.config.DataConfig;
import com.qpf.website.web.config.RootConfig;
import org.apache.commons.beanutils.BeanMap;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.DigestUtils;

import java.util.*;

// Web环境
@WebAppConfiguration
@ContextConfiguration(classes={RootConfig.class, DataConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
//@Ignore
public class TestDataConfig {
    private static final Logger logger = LoggerFactory.getLogger(TestDataConfig.class);
    @Autowired
    private UserDao userDao;
    @Autowired
    private ContentDao contentDao;
    @Autowired
    private ContentCategoryDao contentCategoryDao;

    @Test
    public void testUserDao() {
        User user = userDao.getUserByEmailAndPassword("qpf@qq.com", "e10adc3949ba59abbe56e057f20f883e");
        System.out.println(user);
        user = userDao.getUserByEmail("qpf@qq.com");
        System.out.println(user);
        List<User> users = userDao.getAll();
        System.out.println(users);
    }

    @Ignore
    @Test
    public void testInsert() {
        int insert = 0;
        try {
            User user = new User();
            user.setUsername("test4");
            user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
            user.setEmail("test4@qq.com");
            user.setPhone("13588888888");
            Date now = new Date();
            user.setCreated(now);
            user.setUpdated(now);
            insert = userDao.insert(user);
        } catch (Exception e) {
            logger.error("insert: {}, \n{}", insert, e.getMessage());
        }
//        Assert.assertEquals(insert, 1);
    }


    @Ignore
    @Test
    public void testUpdate() {
        int update = 0;
        try {
            User user = userDao.selectUserById(26);
            logger.info("user: {}", user);
            user.setUsername(null);
            user.setUpdated(new Date());
            update = userDao.update(user);
        } catch (Exception e) {
            logger.error("update: {}, \n{}", update, e.getMessage());
        }
        Assert.assertEquals(update, 1);
    }
    @Test
    public void testDelete() {
        int delete = 0;
        List<String> ids = Arrays.asList(new String[]{"25"});
        try {
            delete = userDao.deleteUserById(ids);
        } catch (Exception e) {
            logger.error("delete: {}, \n{}", delete, e.getMessage());
        }
        Assert.assertEquals(delete, ids.size());
    }
    @Test
    public void testSelectByIds() {
        User user = null;
        try {
            user = userDao.selectById(1);
        } catch (Exception e) {
            logger.error("user: {}, \n{}", user, e.getMessage());
        }
        Assert.assertNotNull(user);
    }
    @Test
    public void testCount() {
        int count = 0;
        try {
            count = userDao.count(new User());
        } catch (Exception e) {
            logger.error("count: {}, \n{}", count, e.getMessage());
        }
        Assert.assertEquals(count, 8);
    }
    @Test
    public void testPage() {
        List<User> users = new ArrayList<>();
        try {
            User user = new User();
            user.setPhone("136");
//            user.setPhone("13610238950");
//            user.setUsername("qpf");
            Map<Object, Object> param = new HashMap<>();
//            param.put("phone", "13610238950");
            param.putAll(new BeanMap(user));
            new BeanMap(user);
            users = userDao.page(param);
        } catch (Exception e) {
            logger.error("users: {}, \n{}", users, e.getMessage());
        }
        Assert.assertEquals(users.size(), 5);
    }

    @Test
    public void testContentCount() {
        int count = 0;
        try {
            count = contentDao.count(new Content());
        } catch (Exception e) {
            logger.error("count: {}, \n{}", count, e.getMessage());
        }
        Assert.assertEquals("Success", count, 4);
    }
    @Test
    public void testContentList() {
        List<Content> contests = new ArrayList<>();
        try {
            contests = contentDao.getAll();
        } catch (Exception e) {
            logger.error("count: {}, \n{}", contests.size(), e.getMessage());
        }
        Assert.assertEquals("Success", contests.size(), 4);
    }
    @Test
    public void testContentInsert() {
        int update = 0;
        try {
            Content content = new Content();
            content.setCategoryId(97);
            content.setTitle("测试");
            content.setTitleDesc("测试");
            content.setSubTitle("测试");
            content.setContent("测试");
            content.setUrl("测试");
            content.setPic("测试");
            Date now = new Date();
            content.setCreated(now);
            content.setUpdated(now);
            update = contentDao.insert(content);
        } catch (Exception e) {
            logger.error("count: {}, \n{}", update, e.getMessage());
        }
        Assert.assertEquals("Success", update, 1);
    }
    @Test
    public void testContentUpdate() {
        int update = 0;
        try {
            Content content = contentDao.selectById(32);
            content.setCategoryId(89);
            Date now = new Date();
            content.setUpdated(now);
            update = contentDao.update(content);
        } catch (Exception e) {
            logger.error("count: {}, \n{}", update, e.getMessage());
        }
        Assert.assertEquals("Success", update, 1);
    }
    @Test
    public void testContentCateGoryList() {
        List<ContentCategory> contests = new ArrayList<>();
        try {
            contests = contentCategoryDao.getAll();
        } catch (Exception e) {
            logger.error("count: {}, \n{}", contests.size(), e.getMessage());
        }
        System.out.println(contests);
        Assert.assertEquals("Success", contests.size(), 13);
    }
}
