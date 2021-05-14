package com.demo.demo.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.demo.demo.studentben.Student;


@Mapper
public interface studentMaper {
    @Select("select * from t_user where id = #{id}")
    public Student getStuByID(Integer id);

}
