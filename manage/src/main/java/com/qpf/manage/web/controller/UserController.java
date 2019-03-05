package com.qpf.manage.web.controller;

import com.qpf.manage.entity.User;
import com.qpf.manage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping({"login", ""})
    public String login() {
        return "login";
    }
    @PostMapping("login")
    public String doLogin(String email, String password) {
        User user = userService.login(email, password);
        if (user != null) {
            return "main";
        }
        else {
            return login();
        }
    }
}
