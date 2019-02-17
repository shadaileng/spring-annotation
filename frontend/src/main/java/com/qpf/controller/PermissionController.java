package com.qpf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("permission")
public class PermissionController {
    @RequestMapping("/list")
    public String list() {
        return "permission/list";
    }
}
