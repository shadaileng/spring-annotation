package com.qpf.controller;

import com.qpf.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    private HelloService helloService;
    @GetMapping("/tomcat")
    @ResponseBody
    public String hello() {
        return helloService.hello("tomcat");
    }
}
