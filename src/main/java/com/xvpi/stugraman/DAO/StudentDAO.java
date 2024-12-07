package com.xvpi.stugraman.DAO;

import com.xvpi.stugraman.beans.Student;
import com.xvpi.stugraman.utils.DBUtil;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO extends GenericDAO<Student> {

    @Override
    protected String getType() {
        return "Student"; // 返回实体类型
    }
    @Override
    public List<Student> getAll() {
        List<Student> entities = new ArrayList<>();
        String query = "SELECT p.person_id, p.name, p.gender, s.major " +
                "FROM Person p " +
                "JOIN Student s ON p.person_id = s.student_id " +
                "WHERE p.type = ?"; // 这里使用 '?' 占位符来绑定参数

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, getType()); // 绑定 'Student'
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                entities.add(createEntity(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }
    @Override
    protected Student createEntity(ResultSet rs) throws SQLException {
        return new Student(
                rs.getString("person_id"),
                rs.getString("name"),
                rs.getString("gender"),
                rs.getString("major")
        );
    }

    public Boolean updateMajor(String studentId, String major) {

        String query = "UPDATE student SET major = ? WHERE student_id = ?";
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, major);
            pstmt.setString(2, studentId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
