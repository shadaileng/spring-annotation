package com.qpf.website.service;

import com.qpf.website.entity.Content;

import java.util.List;

public interface ContentService {
    List<Content> selectByCategoryId(Integer categoryId);
}
