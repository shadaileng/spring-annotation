package com.qpf.website.commons.persistence;

import java.util.List;
import java.util.Map;

public interface BaseDao<T extends BaseEntity> {

    List<T> getAll();

    int insert(T entity);

    int update(T entity);

    int deleteById(List<String> ids);

    T selectById(int id);

    int count(T entity);

    List<T> page(Map<Object, Object> map);
}
