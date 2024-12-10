package com.xvpi.stugraman.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvpi.stugraman.beans.Student;
import com.xvpi.stugraman.beans.Teacher;
import com.xvpi.stugraman.beans.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {
    @Select("SELECT name from person where person_id = #{arg0}")
    String getTeacherNameById(String Id);
    @Select("SELECT COUNT(*) FROM teacher where teacher_id = #{arg0}")
    Boolean findById(String teacherId);
    @Select("SELECT p.person_id, p.name, p.gender, t.title FROM Person p " +
            "JOIN Teacher t ON p.person_id = t.teacher_id " +
            "WHERE p.name LIKE CONCAT('%', #{arg0}, '%')")
    List<Teacher> getTeacherByName(String name);
    @Update( "UPDATE teacher SET title = #{arg1} WHERE teacher_id = #{arg0}")
    boolean updateTitle(String id, String title);
    @Delete("DELETE FROM teacher WHERE teacher_id = #{arg0}")
    int deleteTeacherById(String id);
    @Insert("INSERT INTO teacher (teacher_id, title, person_id) VALUES (#{arg0},#{arg1},#{arg2})")
    int addTeacher(String id, String title, String id1);
    @Select("SELECT * FROM teacher where teacher_id = #{arg0}")
    Teacher getTeacherById(String id);
    @Select("SELECT class_id FROM class where teacher_id = #{arg0}")
    List<String> getClassIdsByTeacherId(String id);
    @Select("SELECT p.person_id, t.teacher_id, p.name, p.gender, t.title, p.type " +
            "FROM Person p " +
            "JOIN Teacher t ON p.person_id = t.teacher_id " +
            "WHERE p.type = 'teacher'")
    List<Teacher> getAllTeachers();
    @Delete("DELETE FROM teacher")
    void deleteAll();
}
