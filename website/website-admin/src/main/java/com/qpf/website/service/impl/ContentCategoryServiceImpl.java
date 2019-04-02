package com.qpf.website.service.impl;

import com.qpf.website.absract.AbstractServiceImpl;
import com.qpf.website.commons.dto.BaseResult;
import com.qpf.website.commons.utils.BeanValidator;
import com.qpf.website.dao.ContentCategoryDao;
import com.qpf.website.entity.ContentCategory;
import com.qpf.website.service.ContentCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ContentCategoryServiceImpl extends AbstractServiceImpl<ContentCategory, ContentCategoryDao> implements ContentCategoryService {
    private final static Logger logger = LoggerFactory.getLogger(ContentCategoryServiceImpl.class);

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
    public BaseResult delete(List<Integer> ids) {
        BaseResult result = BaseResult.success("删除成功");

        List<Integer> parentIds = dao.selectParentIdByIds(ids);
        if (dao.deleteById(ids) <= 0) {
            logger.warn("删除记录失败");
        }
        if (dao.reduce(parentIds, 0) <= 0) {
            logger.warn("父级标志修改失败");
        }
        return result;
    }


    @Override
    public BaseResult save(ContentCategory entity) {

        String validator = BeanValidator.validator(entity);
        if (validator != null) {
            return BaseResult.failed(validator);
        }

        BaseResult result = BaseResult.success();
        Date now = new Date();
        entity.setUpdated(now);
        // 更新
        if (entity.getId() != null) {

            ContentCategory contentCategory = dao.selectById(entity.getId());
            Integer _pid = contentCategory.getParentId();
            Integer pid_ = entity.getParentId();
            if (dao.update(entity) <= 0) {
                result = BaseResult.failed("更新失败");
            }
            if (_pid != null && !_pid.equals(pid_)) {
                // 原来父级标志-1
                int reduce = dao.reduce(Collections.singletonList(_pid), entity.getId());
                // 新的父级标志+1
                int increase = dao.increase(Collections.singletonList(pid_), entity.getId());
                if (increase <= 0 || reduce <= 0) {
                    throw new RuntimeException("父级标志修改失败");
                }
            }
        }
        // 新增
        else {
            entity.setCreated(now);
            if (dao.insert(entity) <= 0) {
                result = BaseResult.failed("新增失败");
            }
            int increase = dao.increase(Collections.singletonList(entity.getParentId()), 0);
            if (increase <= 0) {
                throw new RuntimeException("父级标志修改失败");
            }
        }
        return result;
    }
}
