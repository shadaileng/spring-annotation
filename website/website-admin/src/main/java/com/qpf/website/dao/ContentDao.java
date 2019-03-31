package com.qpf.website.dao;

import com.qpf.website.commons.persistence.BaseDao;
import com.qpf.website.entity.Content;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface ContentDao extends BaseDao<Content> {

    @Select("SELECT * FROM CONTENT")
    List<Content> getAll();

    @InsertProvider(type = ContentProvider.class, method = "insert")
    @Options(useGeneratedKeys = true)
    int insert(Content entity);

    @UpdateProvider(type = ContentProvider.class, method = "update")
    int update(Content entity);

    @DeleteProvider(type = ContentProvider.class, method = "deleteByIds")
    int deleteById(@Param("ids") List<String> ids);

    @SelectProvider(type = ContentProvider.class, method = "selectById")
    Content selectById(@Param("id") int id);

    @SelectProvider(type = ContentProvider.class, method = "count")
    int count(Content entity);

    @SelectProvider(type = ContentProvider.class, method = "page")
    List<Content> page(Map<Object, Object> map);
}
