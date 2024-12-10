package com.xvpi.stugraman.entity;

public abstract class Person {
    protected String id;
    protected String name;
    protected String gender;
    protected String type;  // 这个字段可以帮助区分学生、教师等角色

    public Person(String id, String name, String gender, String type) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.type = type;  // 设置类型
    }

    // Getter和Setter方法
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public abstract String getRole();  // 返回角色方法，由子类实现

    @Override
    public String toString() {
        return "Person{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
