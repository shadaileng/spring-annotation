package com.qpf.website.service.impl;

import com.qpf.website.absract.AbstractServiceImpl;
import com.qpf.website.dao.ContentDao;
import com.qpf.website.entity.Content;
import com.qpf.website.service.ContentService;
import org.springframework.stereotype.Service;

@Service
public class ContentServiceImpl extends AbstractServiceImpl<Content, ContentDao> implements ContentService {
}
