package com.xvpi.stugraman.entity;
import com.xvpi.stugraman.DAO.UserDAO;
import com.xvpi.stugraman.beans.User;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Random;


public class Student extends Person {

    private String major;

    public Student(String studentId, String name, String gender, String major) {
        super(studentId, name, gender, "Student");  // 设置类型为 Student
        this.major = major;

    }

    @Override
    public String getRole() {
        return "Student";  // 返回角色
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    @Override
    public String toString() {
        return "Student [id=" + id + ", name=" + name + ", gender=" + gender + ", major=" + major + "]";
    }
}
