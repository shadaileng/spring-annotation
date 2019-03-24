package com.qpf.website.web.controller;

import com.qpf.website.absract.AbstractController;
import com.qpf.website.commons.dto.BaseResult;
import com.qpf.website.entity.User;
import com.qpf.website.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController<User, UserService> {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
//    @Autowired
//    private UserService service;


    @GetMapping("test")
    public String test(Map<String, Object> map) {
        List<User> users = service.list();

        map.put("users", users);

        return "demo";
    }
    @GetMapping("list")
    public String list(Map<String, Object> map) {
        List<User> users = service.list();

        map.put("users", users);

        return "user_list";
    }

    @ResponseBody
    @GetMapping("/{id}")
    public Object get(@PathVariable("id") Integer id) {
        User user = null;
        try {
            user = service.getById(id);
        } catch (Exception e) {
            logger.error("getById({id}): {}", id, e.getMessage());
        }
        return user;
    }
    @ResponseBody
    @PostMapping("add")
    public Object add(User user) {
        BaseResult result;

        try {
            if (service.emailExists(user.getEmail())) {
                result = BaseResult.failed("邮箱已经注册");
            } else {
                user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
                result = service.save(user);
            }
        } catch (Exception e) {
            result = BaseResult.failed("新增用户失败");
        }

        return result;
    }
    @ResponseBody
    @PostMapping("update")
    public Object update(User user) {
        BaseResult result;

        try {
            result = service.save(user);
        } catch (Exception e) {
            result = BaseResult.failed("新增用户失败");
        }

        return result;
    }
    @ResponseBody
    @PostMapping("delete")
    public Object delete(String[] ids) {
        BaseResult result;
        try {
            result = service.delete(Arrays.asList(ids));
//            result = BaseResult.success();
        } catch (Exception e) {
            result = BaseResult.failed("删除用户失败");
        }

        return result;
    }
    @ResponseBody
    @GetMapping("load")
    public Object load() {
//        List<User> users = service.list();

        return service.list();
    }

    @Override
    public User loadObj(HttpServletRequest request) {
        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setEmail(request.getParameter("email"));
        user.setPhone(request.getParameter("phone"));
        return user;
    }
//
//    @ResponseBody
//    @GetMapping("page")
//    public Object loadPage(@RequestParam(required = false, defaultValue = "0") Integer start, @RequestParam(required = false, defaultValue = "2") Integer length) {
////        PageInfo<User> pageInfo = service.page(start, length, new User());
//
//        return service.page(start, length, new User());
//    }
}
