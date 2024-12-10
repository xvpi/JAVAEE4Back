package com.xvpi.stugraman.controller;
import com.xvpi.stugraman.DAO.*;
import com.xvpi.stugraman.beans.Result;
import com.xvpi.stugraman.beans.Student;
import com.xvpi.stugraman.beans.Teacher;
import com.xvpi.stugraman.service.PersonService;
import com.xvpi.stugraman.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/teacher")
@Api(tags = "教师相关端口")
public class TeacherController {
    public final TeacherService teacherService;
    @Autowired //将依赖关系自动注入到类中
    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @ApiOperation("获取对应老师信息")
    @GetMapping("/getAllTeachers")
    public Result getAllTeachers()
    {
        return teacherService.getAllTeachers();
    }
    @ApiOperation("获取对应老师信息")
    @GetMapping("/getTeacherByName")
    public Result getTeacherByName(@RequestParam("name") String name)
    {
        return teacherService.getTeacherByName(name);
    }
    @ApiOperation("通过Id获取对应老师信息")
    @GetMapping("/getTeacherById")
    public Result getTeacherById(@RequestParam("Id") String Id)
    {
        return teacherService.getTeacherById(Id);
    }

    @ApiOperation("换职称操作")
    @PutMapping("/changeTitle")
    public Result changeTitle(@RequestBody Teacher teacher) {
        return teacherService.changeTitle(teacher);
    }
    @ApiOperation("删除对应老师信息")
    @DeleteMapping("/teacherDelete/{id}")
    public Result deleteTeacherbyId(@PathVariable("id") String id) {
        return teacherService.deleteTeacherbyId(id);
    }
    @ApiOperation("新增老师")
    @PostMapping("/addTeacher")
    public Result addTeacher(@RequestBody Teacher teacher) {
        return teacherService.addTeacher(teacher);
    }
    @ApiOperation("改密码操作")
    @PutMapping("/changePassword")
    public Result changePassword(@RequestBody Teacher teacher) {
        return teacherService.changePassword(teacher);
    }
    @ApiOperation("通过Id获取老师教学班信息")
    @GetMapping("/getClassesByTeacherId")
    public Result getClassesByTeacherId(@RequestParam("teacherId") String teacherId)
    {
        return teacherService.getClassesByTeacherId(teacherId);
    }

}
