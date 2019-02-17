package com.qpf.dao;

import com.qpf.bean.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface RoleMapper {
    @Select("SELECT * FROM ROLE WHERE name=#{name}")
    Role selectRoleCondition(Role role);
    @Select("SELECT * FROM ROLE WHERE name=#{name}")
    List<Role> queryCondition(Role role);
//    @Select("SELECT * FROM ROLE LIMIT #{size} OFFSET #{index}")
    @Select("<script>" +
                "SELECT * FROM ROLE " +
                "<where>" +
                    "<if test='param != \"\"'>" +
                        "name like '%'||#{param}||'%'" +
                    "</if>" +
                "</where>" +
                "LIMIT #{size} OFFSET #{index}" +
            "</script>")
    List<Role> queryRolePage(Map<String, Object> map);
    @Select("<script>" +
                "SELECT COUNT(*) FROM ROLE" +
                "<where>" +
                    "<if test='param != \"\"'>" +
                        "name like '%'||#{param}||'%'" +
                    "</if>" +
                "</where>" +
            "</script>")
    int queryCount(Map<String, Object> map);
    @Insert("INSERT INTO ROLE(name) values( #{name})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addRole(Role role);
    @Update("<script>" +
                "UPDATE ROLE " +
                    "<set> " +
                        "<if test='name != null'> name = #{name},</if> " +
                    "</set>" +
                " where id = #{id}" +
            "</script>")
    int editRole(Role role);
    @Select("SELECT * from ROLE WHERE id = #{id}")
    Role queryRoleById(Integer id);
    @Delete("<script>" +
                "DELETE FROM ROLE " +
                "WHERE id in" +
                "<foreach item='id' index='index' collection='list' open='(' separator=',' close=')'>" +
                    "#{id}" +
                "</foreach>" +
            "</script>")
    int deleteRoleByIds(List<Integer> ids);
    @Select("SELECT * FROM ROLE")
    List<Role> queryAll();
}
