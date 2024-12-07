package com.xvpi.stugraman.beans;

import javax.persistence.*;

@Entity
public class Class {
    @Id
    @Column(name = "class_id")
    private String classId;

    private String className;
    private String semester;
    private Integer totalStudents;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    @Column(name = "course_id")
    private String courseId;
    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    @Column(name = "teacher_id")
    private String teacherId;  // 存储 Person 对象的 ID
    public Class(String classId, String className, String semester, String teacherId, String courseId, int totalStudents) {
        this.classId = classId;
        this.className = className;
        this.semester = semester;
        this.teacherId = teacherId;
        this.courseId = courseId;
        this.totalStudents = totalStudents;
    }

    public Class() {

    }

    // Getter 和 Setter 方法
    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Integer getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(Integer totalStudents) {
        this.totalStudents = totalStudents;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
