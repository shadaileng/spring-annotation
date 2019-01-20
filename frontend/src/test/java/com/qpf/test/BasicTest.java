package com.qpf.test;

import com.qpf.config.RootConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

// Web环境
@WebAppConfiguration
@ContextConfiguration(classes={RootConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class BasicTest {
}
