package com.qpf.website.test;

import com.qpf.website.entity.Content;
import com.qpf.website.service.ContentService;
import com.qpf.website.web.config.DataConfig;
import com.qpf.website.web.config.RootConfig;
import com.qpf.website.web.config.properties.ResourceProperties;
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
public class ServiceTest {
    @Autowired
    private ContentService contentService;
    @Autowired
    private ResourceProperties resourceProperties;

    @Test
    public void testContentService() {
        List<Content> contents = contentService.selectByCategoryId(89);
        System.out.println(contents);
        System.out.println(resourceProperties.getApiPathV1());
    }


}
