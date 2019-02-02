package com.qpf.controller;

import com.qpf.bean.User;
import com.qpf.bean.dto.AJAXResult;
import com.qpf.bean.dto.Page;
import com.qpf.service.UserService;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;
    @GetMapping("/index")
    public String index() {
        return "user/index";
    }
    @GetMapping("/list")
    public String user() {
        return "user/list";
    }
    @ResponseBody
    @PostMapping("/pageQuery")
    public Object pageQuery(@RequestParam(required = false, defaultValue = "1") Integer index, @RequestParam(required = false, defaultValue = "2") Integer pageSize) {
        AJAXResult result = new AJAXResult();
        try{
            Map<String, Object> map = new HashMap<>();
            map.put("index", (index - 1) * pageSize);
            map.put("size", pageSize);
            Page<User> page = userService.queryUserPage(map);
            result.setSuccess(true);
            result.setData(page);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setSuccess(false);
            result.setError("查询用户失败");
        }
        return result;
    }
    @ResponseBody
    @GetMapping("/query/{id}")
    public Object queryId(@PathVariable("id") Integer id) {
        AJAXResult result = new AJAXResult();
        try{
            User user = userService.queryUserById(id);
            result.setSuccess(true);
            result.setData(user);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setSuccess(false);
            result.setError("查询用户(" + id + ")失败");
        }
        return result;
    }
    @ResponseBody
    @PostMapping("/add")
    public Object add(User user) {
        AJAXResult result = new AJAXResult();
        try{
            int id = userService.addUser(user);
            logger.info("add user: {}", id);
            result.setSuccess(true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setSuccess(false);
            result.setError("添加用户失败");
        }
        return result;
    }
    @ResponseBody
    @PostMapping("/edit")
    public Object edit(User user) {
        AJAXResult result = new AJAXResult();
        try{
            int counts = userService.editUser(user);
            logger.info("edit user: {}", counts);
            result.setSuccess(true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setSuccess(false);
            result.setError("更新用户失败");
        }
        return result;
    }
}
