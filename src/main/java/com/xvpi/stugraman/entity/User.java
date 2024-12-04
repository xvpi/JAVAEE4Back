package com.xvpi.stugraman.entity;

public class User {
    private String userId;        // 用户唯一标识
    private String username;      // 用户登录名
    private String passwordHash;  // 密码的哈希值
    private String role;          // 用户角色 ('student', 'teacher', 'admin')
    private String personId;      // 关联的 Person ID（学生或教师）

    // 构造方法
    public User(String userId, String username, String passwordHash, String role, String personId) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.personId = personId;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", username=" + username + ", role=" + role + ", personId=" + personId + "]";
    }
}
