package com.xvpi.stugraman.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xvpi.stugraman.beans.Course;
import com.xvpi.stugraman.beans.Class;
import com.xvpi.stugraman.beans.Person;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


@Mapper
public interface ClassMapper extends BaseMapper<Person> {
    @Select("SELECT * FROM studb_new.class")
    List<Class> selectAllClasses();

    @Insert("INSERT INTO studb_new.class (class_id, class_name, semester, teacher_id, course_id,total_students)" +
            "VALUES " +
            "(#{classId}, #{className},#{semester}, #{teacherId},#{courseId}, 0)")
    int insertClass(Class aclass);

    @Delete("DELETE FROM studb_new.class WHERE class_id = #{classId}")
    int deleteClassById(String classId);

    @Select("SELECT teacher_id FROM studb_new.class WHERE course_id = #{courseId}")
    List<String> getTeacherIdsByCourseId(String courseId);

    @Select("SELECT name FROM studb_new.person WHERE person_id IN (SELECT teacher_id FROM studb_new.class WHERE " +
            "course_id = #{courseId})")
    List<String> getTeacherNamesByCourseId(String courseId);

    @Select("SELECT * FROM studb_new.course WHERE course_name LIKE CONCAT('%', #{courseName}, '%')")
    List<Course> selectCourseByName(String courseName);
    @Select("SELECT COUNT(*) FROM class where class_id = #{arg0}")
    Boolean findById(String courseId);
    @Select("SELECT * FROM class where class_id = #{arg0}")
    Class findClassById(String id);
    @Select("SELECT class_id FROM class where course_id = #{arg0}")
    List<String> findClassIdByCourseId(String courseId);
    @Select("SELECT * FROM class where course_id = #{arg0}")
    List<Class> findClassByCourseId(String courseId);
    @Select("SELECT * FROM class where class_id = #{arg0}")
    Class getClassById(String classId);
    @Select("SELECT * FROM class where course_id = #{arg0} ")
    List<Class> getClassesByCourseId(String cid);
    @Select("SELECT class_name FROM class where class_id = #{arg0} ")
    String getClassNameById(String classId);
    @Select("SELECT teacher_id FROM class where class_id = #{arg0} ")
    String getTeacherIdById(String classId);
    @Select("SELECT student_id FROM grade where class_id = #{arg0}")
    List<String> getStudentsByClassId(String id);

}
