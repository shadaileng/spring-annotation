package com.qpf.controller;

import com.qpf.bean.User;
import com.qpf.bean.dto.AJAXResult;
import com.qpf.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
public class DispatchController {
    private static Logger logger = LoggerFactory.getLogger(DispatchController.class);
    @Autowired
    private UserService userService;
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @ResponseBody
    @PostMapping("/login")
    public AJAXResult doLogin(User user, HttpSession session) {
        logger.info("user: {}", user);
        AJAXResult result = new AJAXResult();
        user = userService.queryUser(user);

        if (user != null) {
            result.setSuccess(true);
            session.setAttribute("user", user);
        } else {
            result.setSuccess(false);
            result.setError("登陆帐号或者密码不正确");
        }

        return result;
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        AJAXResult result = new AJAXResult();
        session.invalidate();
        result.setSuccess(true);
        return "redirect:login";
    }
}
