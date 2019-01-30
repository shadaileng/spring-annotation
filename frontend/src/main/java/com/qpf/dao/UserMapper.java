package com.qpf.dao;

import com.qpf.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    @Select("SELECT * FROM USER WHERE username=#{username} and password = #{password}")
    User selectUserCondition(User user);
    @Select("SELECT * FROM USER LIMIT #{size} OFFSET #{index}")
    List<User> queryUserPage(Map<String, Object> map);
    @Select("SELECT COUNT(*) FROM USER")
    int queryCount(Map<String, Object> map);
    @Insert("INSERT INTO USER(username, password, email) values( #{username}, #{password}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addUser(User user);
    @Update("UPDATE USER SET username = #{username}, password = #{password}, email = #{email} where id = #{id}")
    int editUser(User user);
}
