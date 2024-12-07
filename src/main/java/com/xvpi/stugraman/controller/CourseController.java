package com.xvpi.stugraman.controller;
import com.xvpi.stugraman.DAO.*;
import com.xvpi.stugraman.beans.Course;
import com.xvpi.stugraman.beans.Result;
import com.xvpi.stugraman.beans.Student;
import com.xvpi.stugraman.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/course")
@Api(tags = "课程相关端口")
public class CourseController {
    public final CourseService courseService;
    @Autowired //将依赖关系自动注入到类中
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @ApiOperation("获取课程信息")
    @GetMapping("/getAllCourses")
    public Result getAllCourses() {
            return courseService.getAllCourses();
    }
    @ApiOperation("按课程名称搜索课程")
    @GetMapping("/searchCourse")
    public Result searchCourse(@RequestParam String courseName) {
        return courseService.searchCourseByName(courseName);
    }

    @ApiOperation("删除对应课程信息")
    @DeleteMapping("/deleteCourse/{id}")
    public Result deleteCourse(@PathVariable("id") String courseId) {
        return courseService.deleteCourse(courseId);
    }
    @ApiOperation("新增课程")
    @PostMapping("/addCourse")
    public Result addCourse(@RequestBody Course course) {
        return courseService.addCourse(course);
    }

}
