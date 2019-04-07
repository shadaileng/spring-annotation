package com.qpf.website.service.impl;

import com.qpf.website.absract.AbstractTreeServiceImpl;
import com.qpf.website.dao.ContentCategoryDao;
import com.qpf.website.entity.ContentCategory;
import com.qpf.website.service.ContentCategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ContentCategoryServiceImpl extends AbstractTreeServiceImpl<ContentCategory, ContentCategoryDao> implements ContentCategoryService {
}
