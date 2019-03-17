package com.qpf.website.dao;

import com.qpf.website.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

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

    @Delete("<script>" +
            "DELETE FROM USER WHERE id in" +
            "<foreach item='id' collection='list' open='(' separator=',' close=')'>" +
            " #{id}" +
            "</foreach>" +
            "</script>")
    int deleteUserById(List<String> ids);

    @Select("SELECT * FROM USER WHERE id = #{id}")
    User selectUserById(int id);

    @Select("SELECT COUNT(*) FROM user")
    int count(User user);

    @Select("SELECT * FROM user LIMIT #{length} OFFSET #{start}")
    List<User> page(Map<String, Integer> param);
}
