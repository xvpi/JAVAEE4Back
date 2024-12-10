package com.xvpi.stugraman.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvpi.stugraman.beans.Grade;
import com.xvpi.stugraman.beans.Person;
import com.xvpi.stugraman.beans.User;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface GradeMapper extends BaseMapper<Person> {

    @Delete("DELETE FROM grade WHERE student_id = #{arg0}")
    int deleteGradeById(String id);
    @Select("SELECT class_id FROM grade where student_id = #{arg0}")
    List<String> getClassById(String id);
    @Select("SELECT * FROM grade where student_id = #{arg0}")
    List<Grade> findGradeByStudentId(String sid);
    @Select("SELECT grank FROM (SELECT studb_new.grade.student_id,RANK() OVER (ORDER BY studb_new.grade.total_score " +
            "DESC) AS grank " +
            "FROM grade WHERE class_id = #{arg1}) AS ranked_grades WHERE student_id = #{arg0}")
    Integer getRankById(String sid, String classId);
    @Select("SELECT * FROM grade where class_id = #{arg0}")
    List<Grade> findGradeByClassId(String cid);
    @Select("SELECT * FROM grade where student_id = #{arg0} and class_id = #{arg1}")
    Grade findGradeBySidCid(String studentId, String id);
    @Update( "UPDATE grade SET regular_score = #{arg2} , midterm_score = #{arg3} , lab_score = #{arg4} , " +
            "final_score = #{arg5} , total_score = #{arg6} , grade_date = #{arg7} WHERE class_id = " +
            "#{arg1} and " +
            "student_id = #{arg0}")
    boolean saveStudentScores(String sid, String cid, int rs, int ms, int ls, int fs, int ts, Date gd);
    @Select("SELECT s.student_id, p.name AS student_name, c.course_name, g.total_score, g.grade_date "+
            "FROM Student s " +
            "JOIN Person p ON s.student_id = p.person_id "+
            "JOIN Grade g ON s.student_id = g.student_id "+
            "JOIN Class cl ON g.class_id = cl.class_id "+
            "JOIN Course c ON cl.course_id = c.course_id")
    List<Map<String, Object>> getStudentGradesSummary();
    @Delete("DELETE FROM grade ")
    void deleteAllGrades();
    @Insert("INSERT INTO studb_new.grade (student_id, class_id, regular_score, midterm_score, lab_score,final_score," +
            "total_score, grade_date) VALUES (#{student_id}, #{class_id}, #{regular_score}, #{midterm_score}, " +
            "#{lab_score}," +
            "#{final_score},#{total_score}, #{grade_date})")
    void insertGrade(Grade grade);
}
