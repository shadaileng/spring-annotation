package com.qpf.website.web.controller;

import com.qpf.website.absract.AbstractController;
import com.qpf.website.commons.dto.BaseResult;
import com.qpf.website.entity.ContentCategory;
import com.qpf.website.service.ContentCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController extends AbstractController<ContentCategory, ContentCategoryService> {
    private final static Logger logger = LoggerFactory.getLogger(ContentCategoryController.class);
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
        List<ContentCategory> contentCategories = new ArrayList<>();
        try {
            if (id == null) {
                id = 0;
            }
            contentCategories = service.getByPid(id);
        } catch (Exception e) {
            logger.error(String.format("Error: %s", e.getMessage()));
        }
        return contentCategories;
    }

    @Override
    @ResponseBody
    @PostMapping("add")
    public Object add(ContentCategory entity) {
        BaseResult result;
        try {
            entity.setParentId(entity.getParent().getId());
            entity.setStatus(1);
            entity.setIsParent(0);
            result = service.save(entity);
        } catch (Exception e) {
            result = BaseResult.failed("Error:" + e.getMessage());
        }
        return result;
    }

    @Override
    @ResponseBody
    @PostMapping("update")
    public Object update(ContentCategory entity) {

        BaseResult result;
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
    public Object delete(Integer[] ids) {
        BaseResult result;
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
