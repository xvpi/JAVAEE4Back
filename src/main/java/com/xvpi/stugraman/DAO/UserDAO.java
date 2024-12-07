package com.xvpi.stugraman.DAO;

import com.xvpi.stugraman.beans.User;
import com.xvpi.stugraman.utils.DBUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // 插入一门新课程
    public void insertUser(User user) {
        String sql = "INSERT INTO user (user_id, username, password_hash, role ,person_id) VALUES (?, ?,?,?,?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUserId());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPasswordHash());
            stmt.setString(4, String.valueOf(user.getRole()));
            stmt.setString(5, user.getPersonId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 删除所有用户 (用于初始化时清除数据)
    public void deleteAllUsers() {
        String sql = "DELETE FROM user";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
