package com.qpf.website.dao;

import com.qpf.website.commons.persistence.BaseDao;
import com.qpf.website.commons.persistence.BaseTreeDao;
import com.qpf.website.entity.Content;
import com.qpf.website.entity.ContentCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ContentCategoryDao extends BaseTreeDao<ContentCategory> {
    @Select("SELECT a.*,ifnull(b.id, '0') as `parent.id`, ifnull(b.name, '/') as `parent.name` FROM CONTENT_CATEGORY a left outer join CONTENT_CATEGORY b on a.parent_id = b.id order by a.parent_id, a.`sort_order`, a.`is_parent`")
    List<ContentCategory> getAll();

    @InsertProvider(type = ContentCategoryProvider.class, method = "insert")
    @Options(useGeneratedKeys = true)
    int insert(ContentCategory entity);

    @UpdateProvider(type = ContentCategoryProvider.class, method = "update")
    int update(ContentCategory entity);

    @DeleteProvider(type = ContentCategoryProvider.class, method = "deleteByIds")
    int deleteById(@Param("ids") List<Integer> ids);

    @SelectProvider(type = ContentCategoryProvider.class, method = "selectParentIdByIds")
    List<Integer> selectParentIdByIds(@Param("ids") List<Integer> ids);
    
    @UpdateProvider(type = ContentCategoryProvider.class, method = "reduce")
    int reduce(@Param("ids") List<Integer> ids, @Param("id") Integer id);

    @UpdateProvider(type = ContentCategoryProvider.class, method = "increase")
    int increase(@Param("ids") List<Integer> ids, @Param("id") Integer id);

    @Select("SELECT a.*,ifnull(b.id, '0') as `parent.id`, ifnull(b.name, '/') as `parent.name` FROM CONTENT_CATEGORY a left outer join CONTENT_CATEGORY b on a.parent_id = b.id WHERE a.id = #{id}")
    ContentCategory selectById(@Param("id") int id);

    @SelectProvider(type = ContentCategoryProvider.class, method = "count")
    int count(ContentCategory entity);

    @Select("SELECT * FROM CONTENT_CATEGORY WHERE parent_id = #{pid}")
    List<ContentCategory> selectByPid(@Param("pid") Integer pid);
}
