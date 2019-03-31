package com.qpf.website.dao;

import com.qpf.website.absract.AbstractProvider;
import com.qpf.website.commons.utils.BeanUtils;
import com.qpf.website.entity.ContentCategory;

public class ContentCategoryProvider extends AbstractProvider {

    {
        fields = BeanUtils.loadFields(ContentCategory.class);
        getMethods = BeanUtils.loadMethods(ContentCategory.class);
        tableName = "content_category";
    }
}
