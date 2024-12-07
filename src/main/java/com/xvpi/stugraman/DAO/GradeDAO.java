package com.xvpi.stugraman.DAO;

import com.xvpi.stugraman.beans.Grade;
import com.xvpi.stugraman.utils.DBUtil;

import java.sql.*;
import java.util.*;
import java.sql.Date;


public class GradeDAO {

    //获取所有成绩
    public List<Grade> getAllGrades() {
        List<Grade> grades = new ArrayList<>();
        String sql = "SELECT student_id, class_id, regular_score, midterm_score, lab_score, final_score, total_score, grade_date FROM Grade";


        try (   Connection conn = DBUtil.getConnection();
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String studentId = resultSet.getString("student_id");
                String classId = resultSet.getString("class_id");
                int regularScore = resultSet.getInt("regular_score");
                int midtermScore = resultSet.getInt("midterm_score");
                int labScore = resultSet.getInt("lab_score");
                int finalScore = resultSet.getInt("final_score");
                int totalScore = resultSet.getInt("total_score");
                java.sql.Date gradeDate = resultSet.getDate("grade_date");

                Grade grade = new Grade(studentId, classId, regularScore, midtermScore, labScore, finalScore, totalScore, gradeDate);
                grades.add(grade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return grades;
    }
  //获取各科总成绩，和成绩之和
  public List<Map<String, Object>> getStudentGradesSummary() {
      List<Map<String, Object>> resultList = new ArrayList<>();
      String query = "SELECT s.student_id, p.name AS student_name, c.course_name, g.total_score " +
              "FROM Student s " +
              "JOIN Person p ON s.student_id = p.person_id " + // 连接 Person 表以获取学生姓名
              "JOIN Grade g ON s.student_id = g.student_id " +
              "JOIN Class cl ON g.class_id = cl.class_id " +
              "JOIN Course c ON cl.course_id = c.course_id";

      try (Connection conn = DBUtil.getConnection();
           PreparedStatement pstmt = conn.prepareStatement(query);
           ResultSet rs = pstmt.executeQuery()) {

          Map<String, Map<String, Object>> studentScoresMap = new HashMap<>();
          Set<String> courseNames = new HashSet<>(); // 用于存储所有课程名

          while (rs.next()) {
              String studentId = rs.getString("student_id");
              String studentName = rs.getString("student_name"); // 从 Person 表中获取学生姓名
              String courseName = rs.getString("course_name");
              int totalScore = rs.getInt("total_score");

              // 添加课程名到集合
              courseNames.add(courseName);

              // 创建或更新学生成绩信息
              studentScoresMap.computeIfAbsent(studentId, k -> new HashMap<>()).put("studentName", studentName);
              studentScoresMap.get(studentId).computeIfAbsent("scores", k -> new HashMap<String, Integer>());
              ((Map<String, Integer>) studentScoresMap.get(studentId).get("scores")).put(courseName, totalScore);
          }

          // 计算每个学生的总成绩，并构建结果
          for (Map.Entry<String, Map<String, Object>> entry : studentScoresMap.entrySet()) {
              String studentId = entry.getKey();
              Map<String, Object> studentInfo = entry.getValue();
              Map<String, Integer> scores = (Map<String, Integer>) studentInfo.get("scores");

              int totalGrade = scores.values().stream().mapToInt(Integer::intValue).sum();
              studentInfo.put("totalGrade", totalGrade);

              // 构建行数据
              Map<String, Object> row = new HashMap<>();
              row.put("studentId", studentId);
              row.put("studentName", studentInfo.get("studentName"));
              row.put("scores", scores);
              row.put("totalGrade", totalGrade);

              resultList.add(row);
          }

          // 生成课程名称的列表，用于打印表头
          List<String> courseList = new ArrayList<>(courseNames);
          // 你可以将 courseList 传递给打印函数以生成表头

      } catch (SQLException e) {
          e.printStackTrace();
      }

      return resultList;
  }




    // 获取某教学班的所有学生成绩
    public List<Grade> getGradesByClassId(String classId) {
        List<Grade> grades = new ArrayList<>();
        String query = "SELECT * FROM grade WHERE class_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, classId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Grade grade = new Grade(
                        rs.getString("student_id"),
                        rs.getString("class_id"),
                        rs.getInt("regular_score"),
                        rs.getInt("midterm_score"),
                        rs.getInt("lab_score"),
                        rs.getInt("final_score"),
                        rs.getInt("total_score"),
                        rs.getDate("grade_date")
                );
                grades.add(grade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }

    // 获取某课程的所有学生成绩
    public List<Grade> getGradesByCourseId(String courseId) {
        List<Grade> grades = new ArrayList<>();
        String query = "SELECT g.student_id, g.class_id, g.regular_score, g.midterm_score, g.lab_score, g" +
                ".final_score, g.total_score, g.grade_date " +
                "FROM Grade g " +
                "JOIN Class c ON g.class_id = c.class_id " +
                "WHERE c.course_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, courseId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Grade grade = new Grade(
                        rs.getString("student_id"),
                        rs.getString("class_id"),
                        rs.getInt("regular_score"),
                        rs.getInt("midterm_score"),
                        rs.getInt("lab_score"),
                        rs.getInt("final_score"),
                        rs.getInt("total_score"),
                        rs.getDate("grade_date")
                );
                grades.add(grade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }

    // 获取某教学班的所有学生成绩
    public List<Grade> getGradesByStudentId(String studentId) {
        List<Grade> grades = new ArrayList<>();
        String query = "SELECT * FROM grade WHERE student_id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, studentId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Grade grade = new Grade(
                        rs.getString("student_id"),
                        rs.getString("class_id"),
                        rs.getInt("regular_score"),
                        rs.getInt("midterm_score"),
                        rs.getInt("lab_score"),
                        rs.getInt("final_score"),
                        rs.getInt("total_score"),
                        rs.getDate("grade_date")
                );
                grades.add(grade);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }



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
