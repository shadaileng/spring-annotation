package com.qpf.website.web.controller;

import com.qpf.website.commons.dto.BaseResult;
import com.qpf.website.entity.User;
import com.qpf.website.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @GetMapping("test")
    public String test(Map<String, Object> map) {
        List<User> users = userService.list();

        map.put("users", users);

        return "demo";
    }
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
    @ResponseBody
    @PostMapping("add")
    public Object add(User user) {
        BaseResult result = null;

        try {
            result = userService.save(user);
        } catch (Exception e) {
            result = BaseResult.failed("新增用户失败");
        }

        return result;
    }
    @ResponseBody
    @PostMapping("update")
    public Object update(User user) {
        BaseResult result = null;

        try {
            result = userService.save(user);
        } catch (Exception e) {
            result = BaseResult.failed("新增用户失败");
        }

        return result;
    }
    @ResponseBody
    @PostMapping("delete")
    public Object delete(String[] ids) {
        BaseResult result = null;
        try {
            result = userService.delete(Arrays.asList(ids));
//            result = BaseResult.success();
        } catch (Exception e) {
            result = BaseResult.failed("删除用户失败");
        }

        return result;
    }
    @ResponseBody
    @GetMapping("load")
    public Object load() {
        List<User> users = userService.list();

        return users;
    }
}
