package com.qpf.controller;

import com.qpf.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PersonController {
    @Autowired
    private PersonService personService;
    @GetMapping("/person")
    public Map<String, Object> listPerson() {
        System.out.println(personService);
        Map<String, Object> map = new HashMap<>();
        map.put("success", true);
        map.put("data", personService.queryAll());
        return map;
    }
}
