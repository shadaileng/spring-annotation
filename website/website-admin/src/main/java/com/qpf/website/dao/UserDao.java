package com.qpf.website.dao;

import com.qpf.website.commons.persistence.BaseDao;
import com.qpf.website.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserDao extends BaseDao<User> {

//    @Select("SELECT * FROM USER WHERE email = #{email} AND password = #{password}")
    @SelectProvider(type = UserProvider.class, method = "getUserByEmailAndPassword")
    User getUserByEmailAndPassword(@Param("email") String email, @Param("password") String password);

//    @Select("SELECT * FROM USER WHERE email = #{email}")
    @SelectProvider(type = UserProvider.class, method = "getUserByEmail")
    User getUserByEmail(@Param("email") String email);

    @Select("SELECT * FROM USER")
    List<User> getAll();

//    @Insert("insert into user(username, password, email, phone, created, updated) values(#{username}, #{password}, #{email}, #{phone}, #{created}, #{updated})")
    @InsertProvider(type = UserProvider.class, method = "insert")
    @Options(useGeneratedKeys = true)
    int insert(User user);

//    @Update("UPDATE USER set username=#{username}, email=#{email}, phone=#{phone}, updated=#{updated} where id=#{id}")
    @UpdateProvider(type = UserProvider.class, method = "update")
    int update(User user);

//    @Delete("<script>" +
//            "DELETE FROM USER WHERE id in" +
//            "<foreach item='id' collection='list' open='(' separator=',' close=')'>" +
//            " #{id}" +
//            "</foreach>" +
//            "</script>")
    @DeleteProvider(type = UserProvider.class, method = "deleteByIds")
    int deleteUserById(@Param("ids") List<String> ids);

    @Select("SELECT * FROM USER WHERE id = #{id}")
    User selectUserById(int id);


    @SelectProvider(type = UserProvider.class, method = "selectById")
    User selectById(@Param("id") int id);


//    @Select("SELECT COUNT(*) FROM user")
    @SelectProvider(type = UserProvider.class, method = "count")
    int count(User user);

//    @Select("SELECT * FROM user LIMIT #{length} OFFSET #{start}")
//    List<User> page(Map<String, Integer> param);

    @SelectProvider(type = UserProvider.class, method = "page")
    List<User> page(Map<Object, Object> map);
}
