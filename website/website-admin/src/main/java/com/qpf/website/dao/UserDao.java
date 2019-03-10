package com.qpf.website.dao;

import com.qpf.website.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserDao {
    @Select("SELECT * FROM USER WHERE email = #{email} AND password = #{password}")
    User getUserByEmailAndPassword(@Param("email") String email, @Param("password") String password);
    @Select("SELECT * FROM USER WHERE email = #{email}")
    User getUserByEmail(@Param("email") String email);
    @Select("SELECT * FROM USER")
    List<User> getAll();
    @Insert("insert into user(username, password, email, phone, created, updated) values(#{username}, #{password}, #{email}, #{phone}, #{created}, #{updated})")
    @Options(useGeneratedKeys = true)
    int insert(User user);
    @Update("UPDATE USER set username=#{username}, email=#{email}, phone=#{phone}, updated=#{updated} where id=#{id}")
    int update(User user);
    @Delete("DELETE FROM USER WHERE id = #{id}")
    int deleteUserById(int id);
    @Select("SELECT * FROM USER WHERE id = #{id}")
    User selectUserById(int id);
}
