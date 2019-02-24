package com.qpf.controller;

import com.qpf.bean.Permission;
import com.qpf.bean.dto.AJAXResult;
import com.qpf.service.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("permission")
public class PermissionController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ModelService<Permission> permissionService;
    @RequestMapping("/list")
    public String list() {
        return "permission/list";
    }

    @ResponseBody
    @RequestMapping("/loadData")
    public Object loadData() {
        AJAXResult result = new AJAXResult();
        try {
            List<Permission> permissions = permissionService.queryAll();
//            List<Permission> permissions = new ArrayList<>();
//            Permission root = new Permission();
//            root.setName("控制面板");
//
//            Permission child = new Permission();
//            child.setName("权限管理");
//            root.getChildren().add(child);
//
//            permissions.add(root);
            result.setSuccess(true);
            result.setData(permissions);
        } catch (Exception e) {
            logger.error(e.getMessage());
            result.setSuccess(false);
        }
        return result;
    }
}
