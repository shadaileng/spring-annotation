package com.qpf.website.web.controller.v1;

import com.qpf.website.commons.dto.BaseResult;
import com.qpf.website.entity.User;
import com.qpf.website.service.UserService;
import com.qpf.website.web.dto.UserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.path.v1}/users")
public class UserController {
    @Autowired
    private UserService service;
    @PostMapping("login")
    public BaseResult login(String username, String password) {
        BaseResult result;
        try {
            User user = service.login(username, password);
            if (user != null) {
                UserDTO dto = new UserDTO();
                BeanUtils.copyProperties(user, dto);
                result = BaseResult.success("登陆成功", dto);
            } else {
                result = BaseResult.failed("帐号或者密码错误");
            }
        } catch (Exception e) {
            result = BaseResult.failed(String.format("Error: %s", e.getMessage()));
        }
        return result;
    }
}
