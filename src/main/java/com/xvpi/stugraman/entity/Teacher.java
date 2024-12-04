package com.xvpi.stugraman.entity;

public class Teacher extends Person {
    private String title;

    public Teacher(String teacherId, String name, String gender, String title) {
        super(teacherId, name, gender, "Teacher");  // 设置类型为 Teacher
        this.title = title;
    }

    @Override
    public String getRole() {
        return "Teacher";  // 返回角色
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Teacher [id=" + id + ", name=" + name + ", gender=" + gender + ", title=" + title + "]";
    }
}
