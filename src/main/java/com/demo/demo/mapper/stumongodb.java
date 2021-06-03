package com.demo.demo.mapper;

import com.demo.demo.studentben.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.data.mongodb.core.MongoTemplate;



//注入spring容器
@Component
public class stumongodb {
    @Autowired
    public MongoTemplate MonGo;

//    增
    public void save(Student stu) {
        MonGo.save(stu);
    }

    /**
     * 根据用户名查询对象
     * @return
     */
    public Student findByName(String name) {
        Query query=new Query(Criteria.where("name").is(name));
        return MonGo.findOne(query,Student.class);
    }

    /**
     * 更新对象
     */
    public void update(Student stu) {
        Query query=new Query(Criteria.where("name").is(stu.getName()));
        Update update= new Update().set("age", stu.getAge()).set("name", stu.getName());
        //更新查询返回结果集的第一条
        MonGo.updateFirst(query,update,Student.class);
        //更新查询返回结果集的所有
        // mongoTemplate.updateMulti(query,update,TestEntity.class);
    }

    /**
     * 删除对象
     * @param id
     */
    public void deleteByName(String name) {
        Query query=new Query(Criteria.where("name").is(name));
        MonGo.remove(query,Student.class);
    }


}
