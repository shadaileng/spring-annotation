package com.qpf.service;

import com.qpf.bean.dto.Page;

import java.util.List;
import java.util.Map;

public interface ModelService<T> {
    List<T> queryCondition(T t);

    List<T> queryAll();

    List queryManyById(Integer id);

    Page<T> queryPage(Map<String, Object> map);

    int add(T t) throws Exception;

    int edit(T t) throws Exception;

    T query(T t);

    T queryById(Integer id);

    int deleteByIds(List<Integer> ids);

    boolean assign(Integer id, List<Integer> integers);
}
