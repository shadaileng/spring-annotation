package com.qpf.controller;

import com.qpf.bean.Role;
import com.qpf.bean.User;
import com.qpf.bean.dto.AJAXResult;
import com.qpf.bean.dto.Page;
import com.qpf.service.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/role")
public class RoleController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    @Qualifier("roleService")
    private ModelService<Role> roleService;
    @Autowired
    @Qualifier("userService")
    private ModelService<User> userService;
    @RequestMapping("/list")
    public String list() {
        return "role/list";
    }
    @ResponseBody
    @RequestMapping("/pageQuery")
    public Object pageQuery(@RequestParam(required = false, defaultValue = "") String param, @RequestParam(required = false, defaultValue = "1") Integer index, @RequestParam(required = false, defaultValue = "2") Integer pageSize) {
        AJAXResult result = new AJAXResult();
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("index", (index - 1) * pageSize);
            map.put("size", pageSize);
            map.put("param", param);
            Page<Role> rolePage = roleService.queryPage(map);
            result.setSuccess(true);
            result.setData(rolePage);
        }catch (Exception e) {
            logger.error(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }
    @ResponseBody
    @PostMapping("/add")
    public Object add(Role role) {
        AJAXResult result = new AJAXResult();
        try{
            int add = roleService.add(role);
            result.setSuccess(true);
            result.setData(add);
        } catch (Exception e) {
            result.setSuccess(false);
        }
        return result;
    }
    @ResponseBody
    @PostMapping("/edit")
    public Object edit(Role role) {
        AJAXResult result = new AJAXResult();
        try{
            int edit = roleService.edit(role);
            result.setSuccess(true);
            result.setData(edit);
        } catch (Exception e) {
            result.setSuccess(false);
        }
        return result;
    }
    @ResponseBody
    @PostMapping("/delete")
    public Object delete(Integer[] ids) {
        AJAXResult result = new AJAXResult();
        try{
            int delete = roleService.deleteByIds(Arrays.asList(ids));
            result.setSuccess(true);
            result.setData(delete);
        } catch (Exception e) {
            result.setSuccess(false);
        }
        return result;
    }
}
