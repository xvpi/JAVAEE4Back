package com.xvpi.stugraman.beans;

import javax.persistence.*;
import java.util.Date;

@Entity
public class User {
    @Id
    private String userId;

    private String username;
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "person_id")
    private String personId;  // 存储 Person 对象的 ID

    @ManyToOne
    @JoinColumn(name = "person_id", insertable = false, updatable = false)
    private Person person;  // 关联到 person 表

    private Date createdAt;
    private Date lastLoginAt;

    public User(String userId, String username, String passwordHash, String role, String personId) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = Role.valueOf(role);
        this.personId = personId;
    }

    public User() {

    }

    // Getter 和 Setter 方法
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastLoginAt() {
        return lastLoginAt;
    }

    public void setLastLoginAt(Date lastLoginAt) {
        this.lastLoginAt = lastLoginAt;
    }

    public enum Role {
        student,
        teacher,
        admin
    }
}


