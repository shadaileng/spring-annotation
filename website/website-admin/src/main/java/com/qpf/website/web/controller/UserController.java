package com.qpf.website.web.controller;

import com.qpf.website.entity.User;
import com.qpf.website.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @GetMapping("list")
    public String list(Map<String, Object> map) {
        List<User> users = userService.list();

        map.put("users", users);

        return "user_list";
    }

    @ResponseBody
    @GetMapping("/{id}")
    public Object get(@PathVariable("id") Integer id) {
        User user = null;
        try {
            user = userService.getUserById(id);
        } catch (Exception e) {
            logger.error("getUserById({id}): {}", id, e.getMessage());
        }

        return user;
    }
}
