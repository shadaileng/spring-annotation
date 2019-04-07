package com.qpf.website.dao;

import com.qpf.website.entity.User;
import org.apache.ibatis.annotations.Select;

public interface UserDao {
    @Select("SELECT * FROM USER WHERE username = #{username} OR email = #{username} OR phone = #{username}")
    User login(String username);
}
