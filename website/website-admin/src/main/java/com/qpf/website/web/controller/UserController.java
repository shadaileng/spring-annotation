package com.qpf.website.web.controller;

import com.qpf.website.entity.User;
import com.qpf.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("list")

    public String list(Map<String, Object> map) {
        List<User> users = userService.list();

        map.put("users", users);

        return "user_list";
    }
}
