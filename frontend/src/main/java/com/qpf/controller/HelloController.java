package com.qpf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qpf.service.HelloService;

@Controller
public class HelloController {
	@Autowired
	private HelloService helloService;
	@ResponseBody
	@RequestMapping("/tomcat")
	public String hello() {
		return helloService.hello("tomcat");
	}
}
