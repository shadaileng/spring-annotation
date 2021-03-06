package com.qpf.manage.dao;

import com.qpf.manage.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDao {
    @Select("SELECT * FROM USER WHERE email = #{email} AND password = #{password}")
    User getUserByEmailAndPassword(@Param("email") String email, @Param("password")String password);
    @Select("SELECT * FROM USER WHERE email = #{email}")
    User getUserByEmail(@Param("email") String email);
    @Select("SELECT * FROM USER")
    List<User> getAll();
}
