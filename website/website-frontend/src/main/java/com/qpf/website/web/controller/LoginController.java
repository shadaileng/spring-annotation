package com.qpf.website.web.controller;

import com.google.code.kaptcha.Constants;
import com.qpf.website.commons.dto.BaseResult;
import com.qpf.website.web.api.API;
import com.qpf.website.web.api.UserApi;
import com.qpf.website.web.dto.UserDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    @Autowired
    private UserApi api;
    @GetMapping("login")
    public String login(){
        return "login";
    }
    @ResponseBody
    @PostMapping("login")
    public BaseResult doLogin(String username, String password, String validate, HttpServletRequest request){
        BaseResult result = BaseResult.failed();

        if (!checkValidate(validate, request)) {
            return BaseResult.failed("验证码错误");
        }

        UserDTO login = api.login(username, password);
        if (login != null) {
            result = BaseResult.success("登陆成功");
            result.setData(login);
            request.getSession().setAttribute(API.SESSION_USER_KEY, login);
        } else {
            result = BaseResult.failed("登陆失败");
        }

        return result;
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:login";
    }

    private boolean checkValidate(String validate, HttpServletRequest request) {
        boolean result = false;

        CharSequence code = (CharSequence) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        System.out.println(code);
        if (StringUtils.equals(validate, code)) {
            result = true;
        }

        return result;
    }
}
