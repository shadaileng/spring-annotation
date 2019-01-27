package com.qpf.controller;

import com.qpf.bean.User;
import com.qpf.bean.dto.AJAXResult;
import com.qpf.bean.dto.Page;
import com.qpf.service.UserService;

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
    @GetMapping("/user")
    public String user() {
        return "user/user";
    }
    @ResponseBody
    @PostMapping("/pageQuery")
    public Object pageQuery(@RequestParam(required = false, defaultValue = "1") Integer index, @RequestParam(required = false, defaultValue = "2") Integer pageSize) {
        AJAXResult result = new AJAXResult();
        Map<String, Object> map = new HashMap<>();
        map.put("index", (index - 1) * pageSize);
        map.put("size", pageSize);
        Page<User> page = userService.queryUserPage(map);
        result.setSuccess(true);
        result.setData(page);
        return result;
    }
}
