package com.xvpi.stugraman.entity;

public class Class {
    private String classId;
    private String className;
    private String semester;
    private String teacherId;
    private String courseId;

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    private String teacherName;
    private String courseName;
    private int totalStudents;

    public Class() {}

    public Class(String classId, String className, String semester, String teacherId, String courseId, int totalStudents) {
        this.classId = classId;
        this.className = className;
        this.semester = semester;
        this.teacherId = teacherId;
        this.courseId = courseId;
        this.totalStudents = totalStudents;
    }

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

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public int getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(int totalStudents) {
        this.totalStudents = totalStudents;
    }



    @Override
    public String toString() {
        return "Class [classId=" + classId + ", className=" + className + ", semester=" + semester
                + ", teacherId=" + teacherId + ", courseId=" + courseId + ", totalStudents=" + totalStudents + "]";
    }
}
