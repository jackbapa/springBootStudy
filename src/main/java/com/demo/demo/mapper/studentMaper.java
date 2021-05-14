package com.demo.demo.mapper;

import org.apache.ibatis.annotations.*;
import com.demo.demo.studentben.Student;


@Mapper
public interface studentMaper {
    @Select("select * from t_user where id = #{id}")
    public Student getStuByID(Integer id);

    @Select("select * from t_user where name = #{name}")
    public Student getStuByName(String name);

    @Options(useGeneratedKeys = true,keyColumn = "id")
    @Insert("insert into t_user (name,sex,age) values (#{name},#{sex},#{age})")
    public void addStudent(Student stu);

    @Update("update t_user set name =#{name} where id=#{id}")
    int updateName(@Param("id") int id,@Param("name") String name);

    @Delete("delete from t_user where name =#{name}")
    int deleteByName(@Param("name") String name);


}
