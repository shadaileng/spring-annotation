package com.qpf.website.web.controller;

import com.qpf.website.absract.AbstractController;
import com.qpf.website.commons.dto.BaseResult;
import com.qpf.website.entity.ContentCategory;
import com.qpf.website.service.ContentCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController extends AbstractController<ContentCategory, ContentCategoryService> {
    @Override
    @GetMapping({"", "list"})
    public String list(Map<String, Object> map) {
        map.put("datas", service.list());
        return "content_category_list";
    }

    @Override
    @ResponseBody
    @GetMapping("/{id}")
    public Object get(@PathVariable("id") Integer id) {
        BaseResult result = BaseResult.success();
        try {
            ContentCategory contentCategory = service.getById(id);
            if (contentCategory != null) {
                result.setData(contentCategory);
            } else {
                result = BaseResult.failed("查询失败");
            }
        } catch (Exception e) {
            result = BaseResult.failed(e.getMessage());
        }
        return result;
    }


    @ResponseBody
    @PostMapping("/tree/data")
    public Object treeId(Integer id) {
        if (id == null) {
            id = 0;
        }
        List<ContentCategory> contentCategories = service.getByPid(id);
        return contentCategories;
    }

    @Override
    @ResponseBody
    @PostMapping("add")
    public Object add(ContentCategory entity) {
        BaseResult result = BaseResult.success();
        try {
            entity.setParentId(entity.getParent().getId());
            entity.setStatus(1);
            entity.setIsParent(0);
            result = service.save(entity);
            ContentCategory parent = entity.getParent();
            parent.setIsParent(1);
            result = service.save(parent);
        } catch (Exception e) {
            result = BaseResult.failed("Error:" + e.getMessage());
        }
        return result;
    }

    @Override
    @ResponseBody
    @PostMapping("update")
    public Object update(ContentCategory entity) {

        BaseResult result = BaseResult.success();
        try {
            entity.setParentId(entity.getParent().getId());
            result = service.save(entity);
        } catch (Exception e) {
            result = BaseResult.failed(e.getMessage());
        }
        return result;
    }

    @Override
    @ResponseBody
    @PostMapping("delete")
    public Object delete(String[] ids) {
        BaseResult result = BaseResult.success();
        try {
            result = service.delete(Arrays.asList(ids));
        } catch (Exception e) {
            result = BaseResult.failed(e.getMessage());
        }
        return result;
    }

    @Override
    public Object load() {
        return null;
    }

    @Override
    public ContentCategory loadObj(HttpServletRequest request) {
        return null;
    }
}
