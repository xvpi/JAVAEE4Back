package com.xvpi.stugraman.entity;

import com.xvpi.stugraman.DAO.UserDAO;
import com.xvpi.stugraman.beans.User;

import java.util.Random;

public class Teacher extends Person {
    private String title;
    private final Random random = new Random();
    private final UserDAO userDAO = new UserDAO();
    public Teacher(String teacherId, String name, String gender, String title) {
        super(teacherId, name, gender, "Teacher");  // 设置类型为 Teacher
        this.title = title;
        String password = String.format("%d", random.nextInt(1000000));
        com.xvpi.stugraman.beans.User user = new User(teacherId,name,password,"teacher",teacherId);
        userDAO.insertUser(user);
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
