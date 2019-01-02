package com.qpf.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.qpf.bean.Person;

@Mapper
public interface PersonMapper {
	@Select("select * from person where id = #{id}")
	Person selectPersonById(@Param("id") int id);
}
