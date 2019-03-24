package com.qpf.website.commons.persistence;

import com.qpf.website.commons.dto.BaseResult;
import com.qpf.website.commons.dto.PageInfo;

import java.util.List;

public interface BaseService<T extends BaseEntity> {

    BaseResult save(T entity);

    List<T> list();

    BaseResult delete(List<String> ids);

    T getById(int id);

    PageInfo<T> page(Integer start, Integer length, T entity);
}
