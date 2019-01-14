package com.qpf.test;

import com.qpf.config.RootConfig;
import com.qpf.config.WebConfig;
import com.qpf.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qpf.bean.Person;
import com.qpf.config.DataConfig;
import com.qpf.dao.PersonMapper;

import java.util.List;

@ContextConfiguration(classes={DataConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class BasicTest {
}
