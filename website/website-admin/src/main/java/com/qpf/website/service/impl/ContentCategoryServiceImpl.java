package com.qpf.website.service.impl;

import com.qpf.website.absract.AbstractServiceImpl;
import com.qpf.website.commons.dto.BaseResult;
import com.qpf.website.dao.ContentCategoryDao;
import com.qpf.website.entity.ContentCategory;
import com.qpf.website.service.ContentCategoryService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ContentCategoryServiceImpl extends AbstractServiceImpl<ContentCategory, ContentCategoryDao> implements ContentCategoryService {
    @Override
    public List<ContentCategory> list() {
        List<ContentCategory> list = super.list();
        List<ContentCategory> target = new ArrayList<>();
        Map<Integer, List<ContentCategory>> map = new HashMap<>();
        for (ContentCategory contentCategory : list) {
            Integer parentId = contentCategory.getParentId();
            if (!map.keySet().contains(parentId)) {
                map.put(parentId, new ArrayList<>());
            }
            map.get(parentId).add(contentCategory);
        }
        sort_list(map, target, 0);

        return target;
    }

    private void sort_list(Map<Integer, List<ContentCategory>> src, List<ContentCategory> target, int pid) {

        List<ContentCategory> contentCategories = src.get(pid);
        if (contentCategories != null && contentCategories.size() > 0) {
            for (ContentCategory contentCategory : contentCategories) {
                target.add(contentCategory);
                if (contentCategory.getIsParent() > 0) {
                    sort_list(src, target, contentCategory.getId());
                }
            }
        }
    }

    @Override
    public List<ContentCategory> getByPid(Integer pid) {
        return dao.selectByPid(pid);
    }

    @Override
    public BaseResult delete(List<String> ids) {
        BaseResult result = BaseResult.success("删除成功");
        try {
            if (dao.deleteById(ids) <= 0) {
                result = BaseResult.failed("删除失败");
            }

        } catch (Exception e) {
            result = BaseResult.failed(String.format("删除失败: %s", e.getMessage()));
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }
}
