package com.demo.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.demo.demo.mapper.studentMaper;
import com.demo.demo.studentben.Student;



@RestController
public class stuContrler {
    @Autowired
   studentMaper studentMaper;
    @GetMapping("/{id}")
    public String getOne(@PathVariable Integer id) {
        Student stu = studentMaper.getStuByID(id);
        return stu.name;
    }


//    @GetMapping("/{id}")
//    public Students findOneById(@PathVariable Integer id) {
//        System.out.println("id=========" + id);
//        return studentsService.findOneById(id);


}
