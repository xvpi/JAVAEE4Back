package com.xvpi.stugraman.strategy;

import com.xvpi.stugraman.DAO.CourseDAO;
import com.xvpi.stugraman.beans.Class;
import com.xvpi.stugraman.beans.Course;

import static com.xvpi.stugraman.strategy.SearchStrategy.classDAO;

public class GetCourseName {
    // 获取课程名称的辅助方法教学班id得到课程名称
    public static String getCourseName(String classId) {
        Class classInfo = classDAO.getClassById(classId);
        String courseName = "未知课程";
        if (classInfo != null) {
            Course courseInfo = CourseDAO.getCourseById(classInfo.getCourseId());
            courseName = (courseInfo != null) ? courseInfo.getCourseName() : "未知课程";
        } else {
            System.out.println("未找到该教学班。");
        }
        return courseName; // 这里替换为实际的课程名称获取方式
    }
}

