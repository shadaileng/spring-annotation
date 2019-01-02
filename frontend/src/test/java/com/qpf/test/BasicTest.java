package com.qpf.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qpf.bean.Person;
import com.qpf.config.DataConfig;
import com.qpf.dao.PersonMapper;

@ContextConfiguration(classes={DataConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class BasicTest {
	@Autowired
	private PersonMapper personMapper;
	@Test
	public void testPersonMapper() {
		Person person = personMapper.selectPersonById(1);
		System.out.println(person);
	}
}
