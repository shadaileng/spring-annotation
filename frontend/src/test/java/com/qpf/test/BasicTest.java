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

@ContextConfiguration(classes={RootConfig.class, WebConfig.class, DataConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class BasicTest {
	@Autowired
	private PersonMapper personMapper;
	@Autowired
	private PersonService personService;
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
	public void testPersonService() {
        List<Person> list = personService.queryAll();
        System.out.println(list);
    }
	@Test
	public void testLogback() {
        Logger logger = LoggerFactory.getLogger(getClass());
        logger.error("Hello {}", "shadaileng");
    }
}
