package com.qpf.website.web.controller;

import com.qpf.website.web.api.ContentApi;
import com.qpf.website.web.dto.ContentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private ContentApi api;
    @GetMapping({"/index", ""})
    public String index(Map<String, Object> map) {
        List<ContentDTO> list = api.list();
        map.put("list", list);
        return "index";
    }
}
