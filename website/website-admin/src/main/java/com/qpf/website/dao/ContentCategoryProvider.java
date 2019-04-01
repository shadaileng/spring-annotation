package com.qpf.website.dao;

import com.qpf.website.absract.AbstractProvider;
import com.qpf.website.commons.utils.BeanUtils;
import com.qpf.website.entity.ContentCategory;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Map;

public class ContentCategoryProvider extends AbstractProvider {

    {
        fields = BeanUtils.loadFields(ContentCategory.class);
        getMethods = BeanUtils.loadMethods(ContentCategory.class);
        tableName = "content_category";
    }

    public String reduce(Map<String, Object> map) {
        SQL sql = new SQL();

        @SuppressWarnings("unchecked")
        List<String> ids = (List<String>) map.get("ids");

        sql.UPDATE(tableName);

        sql.SET("is_parent = is_parent - 1");

        String str = ids.toString();
        str = str.replace("[", "(");
        str = str.replace("]", ")");
        sql.WHERE(String.format(" id in (select distinct parent_id from CONTENT_CATEGORY where id in %s)", str));

        return sql.toString();
    }
    public String increase(Map<String, Object> map) {
        SQL sql = new SQL();

        @SuppressWarnings("unchecked")
        int pid = (int) map.get("pid");

        sql.UPDATE(tableName);

        sql.SET("is_parent = is_parent + 1");

        sql.WHERE(String.format(" id in (%s)", pid));

        return sql.toString();
    }
}
