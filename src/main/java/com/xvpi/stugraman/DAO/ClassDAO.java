package com.xvpi.stugraman.DAO;

import com.xvpi.stugraman.beans.Class;
import com.xvpi.stugraman.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassDAO {

    // 获取所有教学班
    public List<Class> getAllClasses() {
        List<Class> classes = new ArrayList<>();
        String query = "SELECT * FROM class";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Class clazz = new Class(
                        rs.getString("class_id"),
                        rs.getString("class_name"),
                        rs.getString("semester"),
                        rs.getString("teacher_id"),
                        rs.getString("course_id"),
                        rs.getInt("total_students")
                );
                classes.add(clazz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classes;
    }

    // 根据教学班号查询教学班
    public Class getClassById(String classId) {
        String query = "SELECT * FROM class WHERE class_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, classId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Class(
                        rs.getString("class_id"),
                        rs.getString("class_name"),
                        rs.getString("semester"),
                        rs.getString("teacher_id"),
                        rs.getString("course_id"),
                        rs.getInt("total_students")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 根据课程ID获取所有相关的教学班
    public List<Class> getClassesByCourseId(String courseId) {
        List<Class> classList = new ArrayList<>();
        String query = "SELECT * FROM class WHERE course_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, courseId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Class cls = new Class(
                        rs.getString("class_id"),
                        rs.getString("class_name"),
                        rs.getString("semester"),
                        rs.getString("teacher_id"),
                        rs.getString("course_id"),
                        rs.getInt("total_students")
                );
                classList.add(cls);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return classList;
    }

    // 根据学生ID获取其所有选修的教学班
    public List<Class> getClassesByStudentId(String studentId) {
        List<Class> classList = new ArrayList<>();
        String query = "SELECT c.* FROM class c " +
                "JOIN grade g ON c.class_id = g.class_id " +
                "WHERE g.student_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Class cl = new Class(
                        rs.getString("class_id"),
                        rs.getString("class_name"),
                        rs.getString("semester"),
                        rs.getString("teacher_id"),
                        rs.getString("course_id"),
                        rs.getInt("total_students")
                );
                classList.add(cl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return classList;
    }


    // 插入教学班
    public void insertClass(Class clazz) {
        String query = "INSERT INTO class (class_id, class_name, semester, teacher_id, course_id, total_students) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, clazz.getClassId());
            pstmt.setString(2, clazz.getClassName());
            pstmt.setString(3, clazz.getSemester());
            pstmt.setString(4, clazz.getTeacherId());
            pstmt.setString(5, clazz.getCourseId());
            pstmt.setInt(6, clazz.getTotalStudents());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除所有教学班
    public void deleteAllClasses() {
        String query = "DELETE FROM class";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
