package com.qpf.website.service.impl;

import com.qpf.website.dao.ContentDao;
import com.qpf.website.entity.Content;
import com.qpf.website.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private ContentDao dao;

    @Override
    public List<Content> selectByCategoryId(Integer categoryId) {
        return dao.selectByCategoryId(categoryId);
    }
}
