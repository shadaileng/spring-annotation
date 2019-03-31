package com.qpf.website.absract;

import com.qpf.website.commons.dto.BaseResult;
import com.qpf.website.commons.dto.PageInfo;
import com.qpf.website.commons.persistence.BaseDao;
import com.qpf.website.commons.persistence.BaseEntity;
import com.qpf.website.commons.persistence.BaseService;
import com.qpf.website.commons.utils.BeanValidator;
import org.apache.commons.beanutils.BeanMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractServiceImpl<T extends BaseEntity, D extends BaseDao> implements BaseService<T> {

    private static final Logger logger = LoggerFactory.getLogger(AbstractServiceImpl.class);

    @Autowired
    protected D dao;

    @Override
    public BaseResult save(T entity) {

        String validator = BeanValidator.validator(entity);
        if (validator != null) {
            return BaseResult.failed(validator);
        }

        BaseResult result = BaseResult.success();
        if (result.getCode() == BaseResult.STATUS_SUCCESS) {
            try {
                Date now = new Date();
                entity.setUpdated(now);
                // 更新
                if (entity.getId() != null) {
                    if (dao.update(entity) <= 0) {
                        result = BaseResult.failed("更新失败");
                    }
                }
                // 新增
                else {
                    entity.setCreated(now);
                    if (dao.insert(entity) <= 0) {
                        result = BaseResult.failed("新增失败");
                    }
                }
            } catch (Exception e) {
                logger.error("save: {}", e.getMessage());
                result = BaseResult.failed(String.format("操作失败: %s", e.getMessage()));
            }
        }

        return result;
    }

    @Override
    public List<T> list() {
        return dao.getAll();
    }

    @Override
    public BaseResult delete(List<String> ids) {
        BaseResult result = BaseResult.success("删除成功");
        try {
            if (dao.deleteById(ids) <= 0) {
                result = BaseResult.failed("删除失败");
            }
        } catch (Exception e) {
            logger.error("delete: {}", e.getMessage());
            result = BaseResult.failed(String.format("删除失败: %s", e.getMessage()));
            throw new RuntimeException(e.getMessage());
        }
        return result;
    }

    @Override
    public T getById(int id) {
        return (T) dao.selectById(id);
    }

    @Override
    public PageInfo<T> page(Integer start, Integer length, T entity) {
        PageInfo<T> page = new PageInfo<>();
        Map<Object, Object> param = new HashMap<>();
        param.put("start", start - 1);
        param.put("length", length);
        param.putAll(new BeanMap(entity));
        List entities = dao.page(param);
        int count = dao.count(entity);
        page.setData(entities);
        page.setTotal(count);
        return page;
    }
//    private BaseResult checkUser(T user) {
//        BaseResult result = BaseResult.success();
//
//        if (StringUtils.isBlank(user.getUsername())) {
//            result = BaseResult.failed("姓名不能为空");
//        }
//        if (StringUtils.isBlank(user.getEmail())) {
//            result = BaseResult.failed("邮箱不能为空");
//        } else if(!RegexpUtils.checkEmail(user.getEmail())) {
//            result = BaseResult.failed("邮箱格式不正确");
//        } else if (user.getId() == null && userDao.getUserByEmail(user.getEmail()) != null) {
//            result = BaseResult.failed("邮箱已注册");
//        }
//        if (StringUtils.isBlank(user.getPhone())) {
//            result = BaseResult.failed("手机号码不能为空");
//        } else if(!RegexpUtils.checkPhone(user.getPhone())) {
//            result = BaseResult.failed("手机号码格式不正确");
//        }
//
//        return result;
//    }
}
