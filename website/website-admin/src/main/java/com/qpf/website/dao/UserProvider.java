package com.qpf.website.dao;

import com.qpf.website.absract.AbstractProvider;
import com.qpf.website.commons.utils.BeanUtils;
import com.qpf.website.entity.User;
import org.apache.ibatis.jdbc.SQL;

import java.util.*;

public class UserProvider extends AbstractProvider {
    {
        fields = BeanUtils.loadFields(User.class);
        getMethods = BeanUtils.loadMethods(User.class);
        tableName = User.class.getSimpleName();

    }
    public String getUserByEmailAndPassword(Map<String, Object> map) {
        return new SQL() {
            {
                SELECT("*");
                FROM("USER");
                WHERE("email = #{email} AND password = #{password}");
            }
        }.toString();
    }
    public String getUserByEmail(Map<String, Object> map) {
        return new SQL() {
            {
                SELECT("*");
                FROM("USER");
                WHERE("email = #{email}");
            }
        }.toString();
    }
}
