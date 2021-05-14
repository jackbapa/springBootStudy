package com.demo.demo.controller;


import com.fasterxml.jackson.databind.util.JSONPObject;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.demo.demo.mapper.studentMaper;
import com.demo.demo.studentben.Student;

import java.util.HashMap;
import java.util.Map;


@RestController
public class stuContrler {
    @Autowired
   studentMaper studentMaper;

    @GetMapping("id/{id}")
    public Map<String, Object> getByID(@PathVariable Integer id) {
        Student stu = studentMaper.getStuByID(id);
        Map<String,Object> rep = new HashMap<String, Object>();
        rep.put("姓名",stu.name);
        rep.put("性别",stu.sex);
        rep.put("年龄",stu.age);
        return rep;
    }

    @GetMapping("name/{name}")
    public Map<String, Object> getByName(@PathVariable String name){
        Student stu = studentMaper.getStuByName(name);
        Map<String,Object> rep = new HashMap<String, Object>();
        rep.put("姓名",stu.name);
        rep.put("性别",stu.sex);
        rep.put("年龄",stu.age);
        return rep;
    }

    @GetMapping("/add/{name}/{sex}/{age}")
    public Map<String, String> addStu(@PathVariable String name,
                                      @PathVariable String sex,
                                      @PathVariable String age)
    {
        Student stu = new Student(name,sex,age);
        Map<String,String> rep = new HashMap<String, String>();
        try{
            studentMaper.addStudent(stu);
            rep.put("status","成功");
        }catch (Exception e){
            rep.put("status","失败");
            rep.put("failreson",e.toString());
        }
        return rep;

    }


}
