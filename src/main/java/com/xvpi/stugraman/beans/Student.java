package com.xvpi.stugraman.beans;

import com.xvpi.stugraman.DAO.UserDAO;
import com.xvpi.stugraman.utils.DBUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

@Entity
public class Student extends Person {
    private final Random random = new Random();

    @Id
    private String studentId;

    @Column(name = "major")
    private String major;

    public Student(String id, String name, String gender, String major) {
        super(id, name, gender, "student");  // 设置类型为 Student
        this.major = major;
    }

    public Student() {
        super();
    }


    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Column(name = "person_id")
    private String personId;  // 存储 Person 对象的 ID
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id", insertable = false, updatable = false)
    private Person person;  // 关联到 person 表

    // Getter 和 Setter 方法
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }


}

