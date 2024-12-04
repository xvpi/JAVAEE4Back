package com.xvpi.stugraman.DAO;

import com.xvpi.stugraman.entity.Teacher;
import com.xvpi.stugraman.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherDAO extends GenericDAO<Teacher> {

    @Override
    protected String getType() {
        return "Teacher"; // 返回实体类型
    }
    @Override
    public List<Teacher> getAll() {
        List<Teacher> entities = new ArrayList<>();
        String query = "SELECT p.id, p.name, p.gender, t.title " +
                "FROM Person p " +
                "JOIN Teacher t ON p.id = t.teacher_id " +
                "WHERE p.type = ?"; // 这里使用 '?' 占位符来绑定参数

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, getType()); // 绑定 'Teacher'
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
    protected Teacher createEntity(ResultSet rs) throws SQLException {
        return new Teacher(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("gender"),
                rs.getString("title")

        );
    }

}