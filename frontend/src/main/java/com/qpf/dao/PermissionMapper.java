package com.qpf.dao;

import com.qpf.bean.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface PermissionMapper {
    @Select("SELECT * from permission")
    List<Permission> queryAll();
}
