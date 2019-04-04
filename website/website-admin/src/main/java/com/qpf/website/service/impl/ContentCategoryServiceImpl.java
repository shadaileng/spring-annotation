package com.qpf.website.service.impl;

import com.qpf.website.absract.AbstractServiceImpl;
import com.qpf.website.absract.AbstractTreeServiceImpl;
import com.qpf.website.commons.dto.BaseResult;
import com.qpf.website.commons.utils.BeanValidator;
import com.qpf.website.dao.ContentCategoryDao;
import com.qpf.website.entity.ContentCategory;
import com.qpf.website.service.ContentCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ContentCategoryServiceImpl extends AbstractTreeServiceImpl<ContentCategory, ContentCategoryDao> implements ContentCategoryService {
}
