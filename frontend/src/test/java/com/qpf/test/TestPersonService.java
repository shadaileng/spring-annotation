package com.qpf.test;

import com.qpf.bean.Person;
import com.qpf.config.DataConfig;
import com.qpf.config.RootConfig;
import com.qpf.config.WebConfig;
import com.qpf.service.PersonService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
// Web环境
@WebAppConfiguration
@ContextConfiguration(classes={RootConfig.class})
public class TestPersonService extends BasicTest {
    @Autowired
    private PersonService personService;
    @Test
    public void TestListPerson() {
        List<Person> list = personService.queryAll();
        System.out.println(list);
    }
}
