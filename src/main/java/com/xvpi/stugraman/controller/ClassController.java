package com.xvpi.stugraman.controller;
import com.xvpi.stugraman.DAO.*;
import com.xvpi.stugraman.beans.*;
import com.xvpi.stugraman.beans.Class;
import com.xvpi.stugraman.service.ClassService;
import com.xvpi.stugraman.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/class")
@Api(tags = "教学班相关端口")
public class ClassController {
    public final ClassService classService;
    @Autowired //将依赖关系自动注入到类中
    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @ApiOperation("获取教学班信息")
    @GetMapping("/getAllClasses")
    public Result getAllClasses() {
        return classService.getAllClasses();
    }
    @ApiOperation("按课程名称搜索教学班")
    @GetMapping("/searchClass")
    public Result searchClass(@RequestParam String courseName) {
        return classService.searchClassByName(courseName);
    }

    @ApiOperation("删除对应教学班信息")
    @DeleteMapping("/deleteClass/{id}")
    public Result deleteClass(@PathVariable("id") String classId) {
        return classService.deleteClass(classId);
    }
    @ApiOperation("新增教学班")
    @PostMapping("/addClass")
    public Result addClass(@RequestBody Class aclass) {
        return classService.addClass(aclass);
    }
    @ApiOperation("通过教学班Id获取对应学生信息")
    @GetMapping("/getStudentsByClassId")
    public Result getStudentsByClassId(@RequestParam("classId") String classId)
    {
        return classService.getStudentsByClassId(classId);
    }
    @ApiOperation("通过教学班Id获取对应教学班信息")
    @GetMapping("/findByClassId")
    public Result findByClassId(@RequestParam("classId") String classId)
    {
        return classService.findByClassId(classId);
    }
    @ApiOperation("在已有学生中进行姓名查找")
    @GetMapping("/getStuByNameId")
    public Result getStuByNameId(@RequestParam("name") String name,@RequestParam("Id") String Id)
    {
        return classService.getStuByNameId(name,Id);
    }
}
