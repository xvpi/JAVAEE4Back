package com.xvpi.stugraman.beans;

import javax.persistence.*;
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Person {
    @Id
    @Column(name = "person_id")
    private String personId;

    private String name;
    private String gender;

    @Column(name = "type", insertable = false, updatable = false)
    private String type;  // 用于区分 Student 和 Teacher
    public Person(String id, String name, String gender, String type) {
        this.personId = id;
        this.name = name;
        this.gender = gender;
        this.type = type;  // 设置类型
    }

    public Person() {

    }

    // Getter 和 Setter 方法
    public String getId() {
        return personId;
    }

    public void setId(String id) {
        this.personId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
