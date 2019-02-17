package com.qpf.dao;

import com.qpf.bean.Role;
import com.qpf.bean.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    @Select("SELECT roleid FROM USER_ROLE WHERE  userid = #{id}")
    List<Integer> queryRolesIdById(Integer id);
    @Select("SELECT * FROM USER WHERE username=#{username} and password = #{password}")
    User selectUserCondition(User user);
//    @Select("SELECT * FROM USER LIMIT #{size} OFFSET #{index}")
    @Select("<script>" +
                "SELECT * FROM USER " +
                "<where>" +
                    "<if test='param != null'>" +
                        "username like '%'||#{param}||'%'" +
                    "</if>" +
                "</where>" +
                "LIMIT #{size} OFFSET #{index}" +
            "</script>")
    List<User> queryUserPage(Map<String, Object> map);
    @Select("<script>" +
                "SELECT COUNT(*) FROM USER" +
                "<where>" +
                    "<if test='param != null'>" +
                        "username like '%'||#{param}||'%'" +
                    "</if>" +
                "</where>" +
            "</script>")
    int queryCount(Map<String, Object> map);
    @Insert("INSERT INTO USER(username, password, email) values( #{username}, #{password}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addUser(User user);
    @Update("<script>" +
                "UPDATE USER " +
                    "<set> " +
                        "<if test='username != null'> username = #{username},</if> " +
                        "<if test='password != null'> password = #{password},</if> " +
                        "<if test='email != null'> email = #{email}</if> " +
                    "</set>" +
                " where id = #{id}" +
            "</script>")
    int editUser(User user);
    @Select("SELECT * from USER WHERE id = #{id}")
    User queryUserById(Integer id);
    @Delete("<script>" +
                "DELETE FROM USER " +
                "WHERE id in" +
                "<foreach item='id' index='index' collection='list' open='(' separator=',' close=')'>" +
                "#{id}" +
                "</foreach>" +
            "</script>")
    int deleteUserByIds(List<Integer> ids);

    @Delete("DELETE FROM USER_ROLE WHERE userid = #{id}")
    int deleteRolesById(Integer id);
    @Insert("<script>" +
                "INSERT INTO USER_ROLE(userid, roleid) values" +
                "<foreach item='roleid' index='index' collection='ids' separator=','>" +
                "(#{id}, #{roleid})" +
                "</foreach>" +
            "</script>")
    int addRolesById(@Param("id") Integer id, @Param("ids") List<Integer> ids);
}
