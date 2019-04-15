package com.qpf.website.web.controller;

import com.qpf.website.absract.AbstractController;
import com.qpf.website.commons.dto.BaseResult;
import com.qpf.website.entity.Content;
import com.qpf.website.service.ContentService;
import com.qpf.website.web.config.properties.ResourceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

@Controller
@RequestMapping("/content")
public class ContentController extends AbstractController<Content, ContentService> {

    @Autowired
    private ResourceProperties resourceProperties;

    @Override
    @GetMapping({"", "list"})
    public String list(Map<String, Object> map) {
        return "content_list";
    }

    @Override
    @ResponseBody
    @GetMapping("/{id}")
    public Object get(@PathVariable("id") Integer id) {
        BaseResult result = BaseResult.success();
        try {
            Content content = service.getById(id);
            result.setData(content);
        } catch (Exception e) {
            result = BaseResult.failed();
        }
        return result;
    }

    @Override
    @ResponseBody
    @PostMapping("add")
    public Object add(Content entity) {
        BaseResult result;
        try {
            if (entity.getPic().contains(resourceProperties.getResourceUrl())) {
                entity.setPic(entity.getPic().substring(resourceProperties.getResourceUrl().length() - 1));
            }
            result = service.save(entity);
        } catch (Exception e) {
            result = BaseResult.failed("新增内容失败");
        }
        return result;
    }

    @Override
    @ResponseBody
    @PostMapping("update")
    public Object update(Content entity) {
        BaseResult result;
        try {
            if (entity.getPic().contains(resourceProperties.getResourceUrl())) {
                entity.setPic(entity.getPic().substring(resourceProperties.getResourceUrl().length() - 1));
            }
            result = service.save(entity);
        } catch (Exception e) {
            result = BaseResult.failed(String.format("修改内容失败: %s", e.getMessage()));
        }
        return result;
    }

    @Override
    @ResponseBody
    @PostMapping("delete")
    public Object delete(Integer[] ids) {
        BaseResult result = BaseResult.success();
        try {
            result = service.delete(Arrays.asList(ids));
        } catch (Exception e) {
            result = BaseResult.failed();
        }
        return result;
    }

    @Override
    @ResponseBody
    @GetMapping("load")
    public Object load() {
        return service.list();
    }

    @Override
    public Content loadObj(HttpServletRequest request) {
        return new Content();
    }
}
