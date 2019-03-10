package com.qpf.website.web.controller;

import com.qpf.website.commons.utils.Constant;
import com.qpf.website.commons.utils.CookieUtils;
import com.qpf.website.entity.User;
import com.qpf.website.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            map.put("isRemember", true);
        }
        logger.info(request.getParameter("msg"));
        return "login";
    }
    @PostMapping("login")
    public String doLogin(String email, String password, boolean isRemember, HttpServletRequest request, HttpServletResponse response, RedirectAttributes map) {
        User user = userService.login(email, password);
        if (user != null) {
            logger.info("isRemember: {}", isRemember);
            request.getSession().setAttribute(Constant.SESSION_USER, user);
            logger.info("set Cookies");
            if (isRemember) {
                CookieUtils.setCookie(request, response, Constant.COOKIE_LOGIN_USER, String.format("%s:%s", email, password), 7 * 24 * 60 * 60);
            } else {
                CookieUtils.setCookie(request, response, Constant.COOKIE_LOGIN_USER, "", 7 * 24 * 60 * 60);
            }
            return "redirect:main";
        }
        else {
            logger.warn("用户登陆失败");
            map.addFlashAttribute("msg", "用户登陆失败");
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
