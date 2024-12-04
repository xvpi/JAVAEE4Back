package com.xvpi.stugraman.DAO;

import com.xvpi.stugraman.entity.Person;
import com.xvpi.stugraman.entity.Student;
import com.xvpi.stugraman.entity.Teacher;
import com.xvpi.stugraman.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
                "LEFT JOIN Student s ON p.id = s.student_id " + // 使用LEFT JOIN以确保获取所有Person
                "WHERE p.id = ? AND p.type = ?";
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
                "LEFT JOIN Student s ON p.id = s.student_id " + // 连接Student表
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
        String personQuery = "INSERT INTO Person (id, name, gender, type) VALUES (?, ?, ?, ?)";
        String specificQuery = null;

        if (entity instanceof Student) {
            specificQuery = "INSERT INTO Student (student_id, major) VALUES (?, ?)";
        } else if (entity instanceof Teacher) {
            specificQuery = "INSERT INTO Teacher (teacher_id, title) VALUES (?, ?)";
        }

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
                    specificStmt.executeUpdate();
                }
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
        String query = "SELECT p.id, p.name, p.gender, s.major " +
                "FROM Person p " +
                "JOIN Grade g ON p.id = g.student_id " +
                "JOIN Student s ON p.id = s.student_id " + // 确保连接到Student表
                "WHERE g.class_id = ? AND p.type = 'Student'";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, classId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Student student = new Student(
                        rs.getString("id"),
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
