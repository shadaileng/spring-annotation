package com.qpf.website.absract;

import com.qpf.website.commons.dto.BaseResult;
import com.qpf.website.commons.persistence.BaseDao;
import com.qpf.website.commons.persistence.BaseEntity;
import com.qpf.website.commons.persistence.BaseTreeDao;
import com.qpf.website.commons.persistence.BaseTreeEntity;
import com.qpf.website.commons.utils.BeanValidator;
import com.qpf.website.entity.ContentCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public abstract class AbstractTreeServiceImpl<T extends BaseTreeEntity, D extends BaseTreeDao> extends AbstractServiceImpl<T, D> {

    private final static Logger logger = LoggerFactory.getLogger(AbstractTreeServiceImpl.class);

    @Autowired
    protected D dao;
    @Override
    public List<T> list() {
        List<T> list = super.list();
        List<T> target = new ArrayList<>();
        Map<Integer, List<T>> map = new HashMap<>();
        for (T entity : list) {
            Integer parentId = entity.getParentId();
            if (!map.keySet().contains(parentId)) {
                map.put(parentId, new ArrayList<>());
            }
            map.get(parentId).add(entity);
        }
        sort_list(map, target, 0);

        return target;
    }

    private void sort_list(Map<Integer, List<T>> src, List<T> target, int pid) {

        List<T> list = src.get(pid);
        if (list != null && list.size() > 0) {
            for (T entity : list) {
                target.add(entity);
                if (entity.getIsParent() > 0) {
                    sort_list(src, target, entity.getId());
                }
            }
        }
    }

    public List<T> getByPid(Integer pid) {
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
    public BaseResult save(T entity) {

        String validator = BeanValidator.validator(entity);
        if (validator != null) {
            return BaseResult.failed(validator);
        }

        BaseResult result = BaseResult.success();
        Date now = new Date();
        entity.setUpdated(now);
        // 更新
        if (entity.getId() != null) {

            T _entity = (T) dao.selectById(entity.getId());
            Integer _pid = _entity.getParentId();
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
