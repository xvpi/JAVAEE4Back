package com.xvpi.stugraman.beans;

import javax.persistence.*;

@Entity
public class Course {
    @Id
    @Column(name = "course_id")
    private String courseId;
    @Column(name = "course_name")
    private String courseName;

    private String teacherNames;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassNames(String className) {
        this.className = className;
    }

    private String classId;
    private String className;
    public String getTeacherNames() {
        return teacherNames;
    }

    public void setTeacherNames(String teacherNames) {
        this.teacherNames = teacherNames;
    }


    public Course(String courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public Course() {

    }

    // Getter 和 Setter 方法
    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }


}

