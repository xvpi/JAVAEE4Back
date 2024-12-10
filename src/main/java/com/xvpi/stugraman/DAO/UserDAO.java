package com.xvpi.stugraman.DAO;

import com.xvpi.stugraman.beans.User;
import com.xvpi.stugraman.utils.DBUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {


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
