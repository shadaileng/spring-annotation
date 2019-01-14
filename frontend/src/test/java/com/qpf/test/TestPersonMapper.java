package com.qpf.test;

import com.qpf.bean.Person;
import com.qpf.dao.PersonMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TestPersonMapper extends BasicTest {
    @Autowired
    private PersonMapper personMapper;
    @Test
    public void testPersonMapper() {
        Person person = personMapper.selectPersonById(1);
        System.out.println(person);
        System.out.println(personMapper.listPerson());
    }
    @Test
    public void testSavePerson() {
        Person person = new Person(null, "qpf", "1");
        int insert = personMapper.insertPerson(person);
        System.out.println(person);
        System.out.println(insert);
    }
    @Test
    public void testLogback() {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.error("Hello {}", "shadaileng");
    }
}
