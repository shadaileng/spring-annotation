package com.qpf.dao;

import com.qpf.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
}
