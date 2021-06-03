package com.demo.demo.mapper;

import org.apache.ibatis.annotations.*;
import com.demo.demo.studentben.Student;

//@Mapper注解交由Spring动态生成这个接口的实现
@Mapper
public interface studentMaper {
    @Select("select * from t_user where id = #{id}")
    public Student getStuByID(Integer id);

    @Select("select * from t_user where name = #{name}")
    public Student getStuByName(String name);

//    如何实现插入时自增id
    /*
    @Options(useGeneratedKeys=true,keyProperty="id",keyColumn="id")
 keyColumn用于指定数据库table中的主键，keyProperty用于指定传入对象的成员变量。
这个注解的意思就是，使用数据库自动增长的主键，并从table中id字段里面把数据放到传入对象的成员变量id里面。
如果我们已经在数据库表中指定了主键，那么keyColumn属性可以缺省。
 */
    @Options(useGeneratedKeys = true,keyColumn = "id")
    @Insert("insert into t_user (name,sex,age) values (#{name},#{sex},#{age})")
    public void addStudent(Student stu);

    @Update("update t_user set name =#{name} where id=#{id}")
    int updateName(
            @Param("id") int id,
            @Param("name") String name);

    @Delete("delete from t_user where name =#{name}")
    int deleteByName(
            @Param("name") String name);


}
