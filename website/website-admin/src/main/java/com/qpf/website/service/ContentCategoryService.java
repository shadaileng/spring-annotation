package com.qpf.website.service;

import com.qpf.website.commons.persistence.BaseService;
import com.qpf.website.entity.ContentCategory;

import java.util.List;

public interface ContentCategoryService extends BaseService<ContentCategory> {
    List<ContentCategory> getByPid(Integer pid);
}
