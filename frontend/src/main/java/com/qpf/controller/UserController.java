package com.qpf.controller;

import com.qpf.bean.User;
import com.qpf.bean.dto.AJAXResult;
import com.qpf.bean.dto.Page;
import com.qpf.service.impl.UserService;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
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
            result.setSuccess(false);
        }
        return result;
    }
    @ResponseBody
    @PostMapping("/add")
    public Object add(User user) {
        AJAXResult result = new AJAXResult();
        try{
            int id = userService.addUser(user);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
        }
        return result;
    }
}
