package com.qpf.controller;

import com.qpf.bean.Role;
import com.qpf.bean.User;
import com.qpf.bean.dto.AJAXResult;
import com.qpf.bean.dto.Page;
import com.qpf.service.ModelService;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    @Qualifier("userService")
    private ModelService<User> userService;
    @Autowired
    @Qualifier("roleService")
    private ModelService<Role> roleService;
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
    public Object pageQuery(@RequestParam(required = false, defaultValue = "") String param, @RequestParam(required = false, defaultValue = "1") Integer index, @RequestParam(required = false, defaultValue = "2") Integer pageSize) {
        AJAXResult result = new AJAXResult();
        try{
            Map<String, Object> map = new HashMap<>();
            map.put("index", (index - 1) * pageSize);
            map.put("size", pageSize);
            map.put("param", param);
            Page<User> page = userService.queryPage(map);
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
            User user = userService.queryById(id);
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
            int id = userService.add(user);
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
            int counts = userService.edit(user);
            logger.info("edit user: {}", counts);
            result.setSuccess(true);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setSuccess(false);
            result.setError("更新用户失败");
        }
        return result;
    }
    @ResponseBody
    @PostMapping("/delete")
    public Object delete(Integer ids[]) {
        AJAXResult result = new AJAXResult();
        try {
            logger.error("ids: " + Arrays.asList(ids));
            int delete = userService.deleteByIds(Arrays.asList(ids));
            logger.info("删除用户数量{}", delete);
            result.setSuccess(true);
            result.setData(delete);
        }catch (Exception e) {
            logger.error(e.getMessage());
            result.setSuccess(false);
            result.setError("用户删除失败");
        }
        return result;
    }
    @ResponseBody
    @PostMapping("/assign/{id}")
    public Object assign(@PathVariable("id") Integer id, Integer roleids[]) {
        AJAXResult result = new AJAXResult();
        try {
            boolean ok = userService.assign(id, Arrays.asList(roleids));
            result.setSuccess(ok);
        }catch (Exception e) {
            result.setSuccess(false);
        }
        return result;
    }
    @ResponseBody
    @RequestMapping("/queryRoles/{id}")
    public Object queryRoleById(@PathVariable Integer id) {
        AJAXResult result = new AJAXResult();
        try {
            List<Role> roles = roleService.queryAll();
            List<Integer> assignedId = userService.queryManyById(id);
            List<Role> unassigned = new ArrayList<>();
            List<Role> assigned = new ArrayList<>();
            roles.forEach(role -> {
                if(assignedId.contains(role.getId())) {
                    assigned.add(role);
                } else {
                    unassigned.add(role);
                }
            });
            Map<String, List<Role>> map = new HashMap<>();
            map.put("assigned", assigned);
            map.put("unassigned", unassigned);
            logger.error(map.toString());
            result.setSuccess(true);
            result.setData(map);
        }catch (Exception e) {
            logger.error(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }
}
