package com.xvpi.stugraman.beans;

import com.xvpi.stugraman.utils.DBUtil;

import javax.persistence.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

@Entity
public class Teacher extends Person {
    private final Random random = new Random();
    @Id
    private String teacherId;

    @Column(name = "title")
    private String title;

    public Teacher(String id, String name, String gender, String title) {
        super(id, name, gender, "teacher");  // 设置类型为 Teacher
        this.title = title;
    }

    public Teacher() {

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
    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
