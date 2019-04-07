package com.qpf.website.dao;

import com.qpf.website.entity.Content;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ContentDao {
    @Select("SELECT * FROM CONTENT WHERE category_id = #{categoryId}")
    List<Content> selectByCategoryId(Integer categoryId);
}
