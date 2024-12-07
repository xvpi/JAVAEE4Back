package com.xvpi.stugraman.DAO;

import com.xvpi.stugraman.beans.Course;
import com.xvpi.stugraman.utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {

    // 插入一门新课程
    public void insertCourse(Course course) {
        String sql = "INSERT INTO course (course_id, course_name) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, course.getCourseId());
            stmt.setString(2, course.getCourseName());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 查询所有课程
    public static List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT * FROM course";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String courseId = rs.getString("course_id");
                String courseName = rs.getString("course_name");
                courses.add(new Course(courseId, courseName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    // 根据课程ID查询课程
    public static Course getCourseById(String courseId) {
        Course course = null;
        String sql = "SELECT * FROM course WHERE course_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, courseId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String courseName = rs.getString("course_name");
                course = new Course(courseId, courseName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return course;
    }

    // 删除所有课程 (用于初始化时清除数据)
    public void deleteAllCourses() {
        String sql = "DELETE FROM course";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
