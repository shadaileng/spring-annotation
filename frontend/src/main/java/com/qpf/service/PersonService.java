package com.qpf.service;

import com.qpf.bean.Person;
import com.qpf.dao.PersonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonService {
    @Autowired
    private PersonMapper personMapper;
    @Transactional(readOnly = true)
    public List<Person> queryAll() {
        return personMapper.listPerson();
    }
}
