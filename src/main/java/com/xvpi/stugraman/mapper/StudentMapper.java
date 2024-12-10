package com.xvpi.stugraman.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvpi.stugraman.beans.Student;
import com.xvpi.stugraman.beans.Teacher;
import com.xvpi.stugraman.beans.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {

    @Delete("DELETE FROM student WHERE student_id = #{arg0}")
    int deleteStudentById(String id);
    @Select("SELECT p.person_id,t.student_id, p.name, p.gender, t.major FROM Person p " +
            "JOIN student t ON p.person_id = t.student_id " +
            "WHERE p.name LIKE CONCAT('%', #{arg0}, '%')")
    List<Student> getStuByName(String name);
    @Select("SELECT COUNT(*) FROM student where student_id = #{arg0}")
    boolean findById(String studentId);

    @Insert("INSERT INTO student (student_id, major, person_id) VALUES (#{arg0},#{arg1},#{arg2})")
    int addStudent(String student_id,String major,String person_id);
    @Select("SELECT * FROM student where student_id = #{arg0}")
    Student getStudentById(String id);
    @Select("SELECT p.person_id,t.student_id, p.name, p.gender, t.major FROM Person p " +
            "JOIN #{arg1} t ON p.person_id = t.student_id " +
            "WHERE p.name LIKE CONCAT('%', #{arg0}, '%')")
    List<Student> getStuByNameStu(String name, List<Student> student);
}
