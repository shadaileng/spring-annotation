package com.qpf.website.web.controller;

import com.qpf.website.commons.Constant;
import com.qpf.website.commons.CookieUtils;
import com.qpf.website.entity.User;
import com.qpf.website.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private UserService userService;
    @GetMapping({"login", ""})
    public String login(HttpServletRequest request, Map<String, Object> map) {
        String loginUser = CookieUtils.getCookieValue(request, Constant.COOKIE_LOGIN_USER);
        if (!StringUtils.isBlank(loginUser)  && StringUtils.contains(loginUser, ":")) {
            String[] split = loginUser.split(":");
            String email = split[0];
            String password = split[1];
            map.put("email", email);
            map.put("password", password);
//            map.put("isRemember", true);
        }
        return "login";
    }
    @PostMapping("login")
    public String doLogin(String email, String password, boolean isRemember, HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        User user = userService.login(email, password);
        logger.info("isRemember: {}", isRemember);
        if (user != null) {
            request.getSession().setAttribute(Constant.SESSION_USER, user);
            if (isRemember) {
                logger.info("set Cookies");
                CookieUtils.setCookie(request, response, Constant.COOKIE_LOGIN_USER, String.format("%s:%s", email, password), 7 * 24 * 60 * 60);
            }
            return "redirect:main";
        }
        else {
            map.put("msg", "用户注册失败");
            return "redirect:login";
        }
    }
    @GetMapping("logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        CookieUtils.deleteCookie(request, response,Constant.COOKIE_LOGIN_USER);
        return "redirect:login";
    }
}
