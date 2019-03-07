package com.qpf.website.web.controller;

import com.qpf.website.commons.Constant;
import com.qpf.website.entity.User;
import com.qpf.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping({"login", ""})
    public String login() {
        return "login";
    }
    @PostMapping("login")
    public String doLogin(String email, String password, HttpServletRequest request) {
        User user = userService.login(email, password);
        if (user != null) {
            request.getSession().setAttribute(Constant.SESSION_USER, user);
            return "main";
        }
        else {
            return login();
        }
    }
}
