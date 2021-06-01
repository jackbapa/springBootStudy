package com.demo.demo.studentben;

public class Student {
    public String name;
    public String sex;
    public String age;
//    应用MongDb时会报错：
//    Cannot autogenerate id of type java.lang.Integer for entity of type
//    因为MongDB会将id作为的唯一id，换句话说，此处id与MongDB的_id键冲突
//    故改为其他，例如objectid
//    public Integer id;
    public Integer objectid;

    public Student(String name,String sex, String age){
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public Integer getId() {
        return objectid;
    }

    public void setId(Integer id) {
        this.objectid = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }



}
