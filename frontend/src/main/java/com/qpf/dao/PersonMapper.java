package com.qpf.dao;

import org.apache.ibatis.annotations.*;

import com.qpf.bean.Person;

import java.util.List;

@Mapper
public interface PersonMapper {
	@Select("select * from person where id = #{id}")
	Person selectPersonById(@Param("id") int id);
	@Select("Select * from person")
	List<Person> listPerson();
	@Insert("insert into person(name,gender) values(#{name}, #{gender})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
	int insertPerson(Person person);
	@Update("update person name = #{person.name}, gender = #{gender} where id = #{id}")
	int updatePersonById(Person person);
	@Delete("delete person where id = #{id}")
	int deletePersonById(@Param("id") int id);
}
