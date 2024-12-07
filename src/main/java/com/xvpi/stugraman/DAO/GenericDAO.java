package com.xvpi.stugraman.DAO;

import com.xvpi.stugraman.beans.*;
import com.xvpi.stugraman.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class GenericDAO<T> {
    protected abstract String getType(); // 返回实体类型（如 'Student' 或 'Teacher'）
    protected abstract T createEntity(ResultSet rs) throws SQLException; // 创建实体对象

    public List<T> getAll() {
        List<T> entities = new ArrayList<>();
        String query = "SELECT * FROM Person WHERE type = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, getType());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                entities.add(createEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    public T getById(String id) {
        String query = "SELECT p.*, s.major FROM Person p " +
                "LEFT JOIN Student s ON p.person_id = s.student_id " + // 使用LEFT JOIN以确保获取所有Person
                "WHERE p.person_id = ? AND p.type = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, id);
            pstmt.setString(2, getType());
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return createEntity(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<T> getByName(String name) {
        List<T> entities = new ArrayList<>();
        String query = "SELECT p.*, s.major FROM Person p " +
                "LEFT JOIN Student s ON p.person_id = s.student_id " + // 连接Student表
                "WHERE p.name = ? AND p.type = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, getType());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                entities.add(createEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }


    public void insert(T entity) {
        String personQuery = "INSERT INTO Person (person_id, name, gender, type) VALUES (?, ?, ?, ?)";
        String specificQuery = null;

        if (entity instanceof Student) {
            specificQuery = "INSERT INTO Student (student_id, major,person_id) VALUES (?, ?, ?)";
        } else if (entity instanceof Teacher) {
            specificQuery = "INSERT INTO Teacher (teacher_id, title,person_id) VALUES (?, ?, ?)";
        }
        String userQuery = "INSERT INTO User (user_id, username, password_hash, role, person_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false); // 启用事务

            // 插入Person表
            try (PreparedStatement personPstmt = conn.prepareStatement(personQuery)) {
                personPstmt.setString(1, ((Person) entity).getId());
                personPstmt.setString(2, ((Person) entity).getName());
                personPstmt.setString(3, ((Person) entity).getGender());
                personPstmt.setString(4, getType());
                personPstmt.executeUpdate();
            }

            // 插入具体表（Student 或 Teacher）
            if (specificQuery != null) {
                try (PreparedStatement specificStmt = conn.prepareStatement(specificQuery)) {
                    specificStmt.setString(1, ((Person) entity).getId());
                    if (entity instanceof Student) {
                        String major = ((Student) entity).getMajor();
                        specificStmt.setString(2, major);

                    } else if (entity instanceof Teacher) {
                        String title = ((Teacher) entity).getTitle();
                        specificStmt.setString(2, title);

                    }
                    specificStmt.setString(3, ((Person) entity).getId());
                    specificStmt.executeUpdate();
                }
            }
            // 插入 User 表（无论是 Student 还是 Teacher 都需要插入 User）
            try (PreparedStatement userStmt = conn.prepareStatement(userQuery)) {
                String username = ((Person) entity).getName();  // 你可以根据需要生成用户名
                Random random = new Random();
                String password = String.format("%d", random.nextInt(1000000));  // 随机生成一个密码
                String role = entity instanceof Student ? "student" : "teacher";  // 根据角色设置

                userStmt.setString(1, ((Person) entity).getId());  // user_id 使用 person_id
                userStmt.setString(2, username);  // 假设用户名是人的名字
                userStmt.setString(3, password);  // 使用生成的密码
                userStmt.setString(4, role);      // 角色是 "student" 或 "teacher"
                userStmt.setString(5, ((Person) entity).getId());  // person_id 是 personId
                userStmt.executeUpdate();
            }

            conn.commit(); // 提交事务
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll() {
        String specificDeleteQuery = null;

        if ("Student".equals(getType())) {
            specificDeleteQuery = "DELETE FROM Student"; // 直接删除所有学生
        } else if ("Teacher".equals(getType())) {
            specificDeleteQuery = "DELETE FROM Teacher"; // 直接删除所有教师
        }

        String personDeleteQuery = "DELETE FROM Person WHERE type = ?";

        try (Connection conn = DBUtil.getConnection()) {
            conn.setAutoCommit(false); // 启用事务

            if (specificDeleteQuery != null) {
                try (PreparedStatement specificStmt = conn.prepareStatement(specificDeleteQuery)) {
                    specificStmt.executeUpdate(); // 删除所有记录
                }
            }

            // 删除Person表
            try (PreparedStatement personStmt = conn.prepareStatement(personDeleteQuery)) {
                personStmt.setString(1, getType());
                personStmt.executeUpdate();
            }

            conn.commit(); // 提交事务
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 根据教学班ID获取所有学生
    public List<Student> getStudentsByClassId(String classId) {
        List<Student> studentList = new ArrayList<>();
        String query = "SELECT p.person_id, p.name, p.gender, s.major " +
                "FROM Person p " +
                "JOIN Grade g ON p.person_id = g.student_id " +
                "JOIN Student s ON p.person_id = s.student_id " + // 确保连接到Student表
                "WHERE g.class_id = ? AND p.type = 'Student'";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, classId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Student student = new Student(
                        rs.getString("person_id"),
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getString("major")
                );
                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentList;
    }

}
