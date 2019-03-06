package com.qpf.manage.dao;

import com.qpf.manage.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDao {
    @Select("SELECT * FROM USER WHERE email = #{email} AND password = #{password}")
    User getUserByEmailAndPassword(String email, String password);
    @Select("SELECT * FROM USER")
    List<User> getAll();
}
