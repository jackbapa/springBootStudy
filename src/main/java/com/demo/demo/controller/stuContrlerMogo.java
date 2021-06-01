package com.demo.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.demo.demo.mapper.studentMaper;
import com.demo.demo.studentben.Student;

import java.util.HashMap;
import java.util.Map;
import com.demo.demo.mapper.stumongodb;

@RestController
public class stuContrlerMogo {
    @Autowired
    stumongodb studentMongo;

    //    依据姓名查询
    @GetMapping("mongo/name/{name}")
    public Map<String, Object> getByName_M(@PathVariable String name){
        Student resStudent= studentMongo.findByName(name);
        Map<String,Object> rep = new HashMap<String, Object>();
        rep.put("姓名",resStudent.name);
        rep.put("性别",resStudent.sex);
        rep.put("年龄",resStudent.age);
        return rep;
    }

    //    增加条目
    @GetMapping("mongo/add/{name}/{sex}/{age}")
    public Map<String, String> addStu_M(@PathVariable String name,
                                      @PathVariable String sex,
                                      @PathVariable String age)
    {
        Student stu = new Student(name,sex,age);
        Map<String,String> rep = new HashMap<String, String>();
        try{
            studentMongo.save(stu);
            rep.put("status","成功");
        }catch (Exception e){
            rep.put("status","失败");
            rep.put("failReson",e.toString());
        }
        return rep;
    }

    //    依据name更新
    @GetMapping("mongo/update/{name}/{sex}/{age}")
    public Map<String, String> update(@PathVariable Integer age,
                                      @PathVariable String name,
                                      @PathVariable String sex){

        Map<String,String> rep = new HashMap<String, String>();
        try{
            studentMongo.update(new Student(name,sex,age.toString()));
        }catch (Exception e){
            rep.put("status","失败");
            rep.put("failReson",e.toString());
            return rep;
        }
        rep.put("status","成功");
        return rep;
    }
//
    //    依据姓名删除条目
    @GetMapping("mongo/delete/{name}")
    public Map<String, String> delete(@PathVariable String name){
        Map<String,String> rep = new HashMap<String, String>();
        try{
           studentMongo.deleteByName(name);
        }catch (Exception e){
            rep.put("status","失败");
            rep.put("failReson",e.toString());
            return rep;
        }
        rep.put("status","成功");
        return rep;
    }

//    //    本地导通测试
//    @GetMapping("test/{id}")
//    public Map<String, Object> get(@PathVariable Integer id) {
////        Student stu = studentMaper.getStuByID(id);
//        Map<String,Object> rep = new HashMap<String, Object>();
//        rep.put("姓名","1");
//        rep.put("性别","2");
//        rep.put("年龄","3");
//        return rep;
//    }
//
//
//
//
}
