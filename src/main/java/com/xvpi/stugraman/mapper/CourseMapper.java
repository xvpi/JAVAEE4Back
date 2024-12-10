package com.xvpi.stugraman.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvpi.stugraman.beans.Course;
import com.xvpi.stugraman.beans.Person;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface CourseMapper extends BaseMapper<Person> {
    @Select("SELECT * FROM studb_new.course")
    List<Course> selectAllCourses();

    @Insert("INSERT INTO studb_new.course (course_id, course_name) VALUES (#{courseId}, #{courseName})")
    int insertCourse(Course course);

    @Delete("DELETE FROM studb_new.course WHERE course_id = #{courseId}")
    int deleteCourse(String courseId);

    @Select("SELECT teacher_id FROM studb_new.class WHERE course_id = #{courseId}")
    List<String> getTeacherIdsByCourseId(String courseId);

    @Select("SELECT name FROM studb_new.person WHERE person_id IN (SELECT teacher_id FROM studb_new.class WHERE " +
            "course_id = #{courseId})")
    List<String> getTeacherNamesByCourseId(String courseId);
    @Select("SELECT course_name from course where course_id = #{arg0}")
    String getCourseNameById(String Id);
    @Select("SELECT * FROM studb_new.course WHERE course_name LIKE CONCAT('%', #{courseName}, '%')")
    List<Course> selectCourseByName(String courseName);
    @Select("SELECT COUNT(*) FROM course where course_id = #{arg0}")
    Boolean findById(String courseId);
    @Select("SELECT course_id FROM course where course_name LIKE CONCAT('%', #{courseName}, '%')")
    String findIdByName(String courseName);
    @Select("SELECT * FROM course where course_id = #{arg0}")
    List<Course> getCoursesById(String id);

}
