package com.demo.demo.controller;


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

//    依据id查询
    @GetMapping("id/{id}")
    public Map<String, Object> getByID(@PathVariable Integer id) {
        Student stu = studentMaper.getStuByID(id);
        Map<String,Object> rep = new HashMap<String, Object>();
        rep.put("姓名",stu.name);
        rep.put("性别",stu.sex);
        rep.put("年龄",stu.age);
        return rep;
    }

//    依据姓名查询
    @GetMapping("name/{name}")
    public Map<String, Object> getByName(@PathVariable String name){
        Student stu = studentMaper.getStuByName(name);
        Map<String,Object> rep = new HashMap<String, Object>();
        rep.put("姓名",stu.name);
        rep.put("性别",stu.sex);
        rep.put("年龄",stu.age);
        return rep;
    }

//    增加条目
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
            rep.put("failReson",e.toString());
        }
        return rep;
    }

//    依据id更新姓名
    @GetMapping("/update/{id}/{name}")
    public Map<String, String> update(@PathVariable Integer id,
                                      @PathVariable String name){

        Map<String,String> rep = new HashMap<String, String>();
        try{
            studentMaper.updateName(id,name);
            }catch (Exception e){
            rep.put("status","失败");
            rep.put("failReson",e.toString());
            return rep;
        }
        rep.put("status","成功");
        return rep;
    }

//    依据姓名删除条目
    @GetMapping("/delete/{name}")
    public Map<String, String> delete(@PathVariable String name){
        Map<String,String> rep = new HashMap<String, String>();
        try{
            studentMaper.deleteByName(name);
        }catch (Exception e){
            rep.put("status","失败");
            rep.put("failReson",e.toString());
            return rep;
        }
        rep.put("status","成功");
        return rep;
    }

//    本地导通测试
    @GetMapping("test/{id}")
    public Map<String, Object> get(@PathVariable Integer id) {
//        Student stu = studentMaper.getStuByID(id);
        Map<String,Object> rep = new HashMap<String, Object>();
        rep.put("姓名","1");
        rep.put("性别","2");
        rep.put("年龄","3");
        return rep;
    }



}
