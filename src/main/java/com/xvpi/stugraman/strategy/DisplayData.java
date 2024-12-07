package com.xvpi.stugraman.strategy;

import com.xvpi.stugraman.beans.Class;
import com.xvpi.stugraman.beans.Grade;

import java.util.List;

public class DisplayData {
    private String studentId;
    private List<Grade> grades;
    private Class cl;

    // 构造函数
    public DisplayData(Class cl) {
        this.cl = cl;
    }

    public DisplayData(String studentId) {
        this.studentId = studentId;
    }

    public DisplayData(List<Grade> grades) {
        this.grades = grades;
    }

    // Getter 方法
    public String getStudentId() {
        return studentId;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public Class getCl() {
        return cl;
    }


}
