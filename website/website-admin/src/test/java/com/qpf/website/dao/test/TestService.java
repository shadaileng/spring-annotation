package com.qpf.website.dao.test;

import com.qpf.website.commons.dto.BaseResult;
import com.qpf.website.commons.dto.PageInfo;
import com.qpf.website.dao.UserDao;
import com.qpf.website.entity.Content;
import com.qpf.website.entity.ContentCategory;
import com.qpf.website.entity.User;
import com.qpf.website.service.ContentCategoryService;
import com.qpf.website.service.ContentService;
import com.qpf.website.service.UserService;
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
import org.springframework.beans.factory.annotation.Qualifier;
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
public class TestService {
    private static final Logger logger = LoggerFactory.getLogger(TestService.class);

    @Autowired()
    @Qualifier("userService")
    private UserService userService;

    @Autowired()
//    @Qualifier("contentService")
    private ContentService contentService;
    @Autowired()
//    @Qualifier("contentService")
    private ContentCategoryService contentCategoryService;

    @Test
    public void testGetAll() {
        List<User> list = userService.list();
        logger.info(String.format("%s", list));
    }
    @Test
    public void testPage() {
        PageInfo<User> page = userService.page(0, 5, new User());
        logger.info(String.format("%s", page));
    }
    @Test
    public void testGetById() {
        User user = userService.getById(1);
        logger.info(String.format("%s", user.getId()));
    }
    @Test
    public void testInsert() {
        User user = new User();
        user.setUsername("test5");
        user.setPhone("13300000000");
        user.setEmail("test5@qq.com");
        user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        Date now = new Date();
        user.setCreated(now);
        user.setUpdated(now);
        BaseResult save = userService.save(user);
        logger.info(String.format("%s", save));
    }
    @Test
    public void testUpdate() {
        User user = userService.getById(32);
        user.setUsername("test10");
        BaseResult save = userService.save(user);
        logger.info(String.format("%s", save));
    }

    @Test
    public void testPageContent() {
        PageInfo<Content> page = contentService.page(0, 5, new Content());
        logger.info(String.format("%s", page));
    }
    @Test
    public void testContentById() {
        Content content = contentService.getById(28);
        logger.info(String.format("content: %s", content));
    }
    @Test
    public void testContentContengCategorylist() {
        List<ContentCategory> contentCategorys = contentCategoryService.list();
        System.out.println(contentCategorys);
    }
    @Test
    public void testContentContengCategorydelete() {
        BaseResult delete = contentCategoryService.delete(Arrays.asList(100));
        System.out.println(delete);
    }
    @Test
    public void testContentContengCategorySave() {
        ContentCategory contentCategory = contentCategoryService.getById(96);
        contentCategory.setParentId(93);
        BaseResult save = contentCategoryService.save(contentCategory);
        System.out.println("********************************************");
        System.out.println(String.format("save: %s", save));
        System.out.println("********************************************");
    }
}
