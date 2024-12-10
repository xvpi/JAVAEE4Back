package com.xvpi.stugraman.DAO;

import com.xvpi.stugraman.beans.Grade;
import com.xvpi.stugraman.utils.DBUtil;

import java.sql.*;
import java.util.*;
import java.sql.Date;


public class GradeDAO {

    // 插入成绩
    public void insertGrade(Grade grade) {
        String query = "INSERT INTO grade (student_id, class_id, regular_score, midterm_score, lab_score, " +
                "final_score,total_score, grade_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, grade.getStudentId());
            pstmt.setString(2, grade.getClassId());
            pstmt.setInt(3, grade.getRegularScore());
            pstmt.setInt(4, grade.getMidtermScore());
            pstmt.setInt(5, grade.getLabScore());
            pstmt.setInt(6, grade.getFinalScore());
            pstmt.setInt(7, grade.getTotalScore());
            pstmt.setDate(8, new Date(grade.getGradeDate().getTime()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除单个成绩记录
    public void deleteGrade(String studentId, String classId) {
        String query = "DELETE FROM Grade WHERE student_id = ? AND class_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, studentId);
            pstmt.setString(2, classId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除所有成绩
    public void deleteAllGrades() {
        String query = "DELETE FROM grade";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
