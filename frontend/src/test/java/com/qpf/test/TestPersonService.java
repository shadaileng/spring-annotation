package com.qpf.test;

import com.qpf.bean.Person;
import com.qpf.service.PersonService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
public class TestPersonService extends BasicTest {
    @Autowired
    private PersonService personService;
    @Test
    public void TestListPerson() {
        System.out.println(personService);
        List<Person> list = personService.queryAll();
        System.out.println(list);
    }
}
