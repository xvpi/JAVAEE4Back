package com.xvpi.stugraman.controller;
import com.xvpi.stugraman.DAO.*;
import com.xvpi.stugraman.beans.Result;
import com.xvpi.stugraman.beans.Student;
import com.xvpi.stugraman.beans.Teacher;
import com.xvpi.stugraman.service.PersonService;
import com.xvpi.stugraman.service.StudentService;
import com.xvpi.stugraman.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/student")
@Api(tags = "学生相关端口")
public class StudentController {
    public final StudentService studentService;
    @Autowired //将依赖关系自动注入到类中
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @ApiOperation("获取对应学生信息")
    @GetMapping("/getStudentById")
    public Result getStudentByName(@RequestParam("Id") String Id)
    {
        return studentService.getStudentById(Id);
    }
    @ApiOperation("获取学生对应课程信息")
    @GetMapping("/getClassesById")
    public Result getClassesById(@RequestParam("Id") String Id)
    {
        return studentService.getClassesById(Id);
    }

    @ApiOperation("改密码操作")
    @PutMapping("/changePassword")
    public Result changePassword(@RequestBody Student student) {
        return studentService.changePassword(student);
    }


//    @ApiOperation("换职称操作")
//    @PutMapping("/changeTitle")
//    public Result changeTitle(@RequestBody Teacher teacher) {
//        return teacherService.changeTitle(teacher);
//    }
//    @ApiOperation("删除对应学生信息")
//    @DeleteMapping("/teacherDelete/{id}")
//    public Result deleteTeacherbyId(@PathVariable("id") String id) {
//        return teacherService.deleteTeacherbyId(id);
//    }
//    @ApiOperation("新增学生")
//    @PostMapping("/addTeacher")
//    public Result addTeacher(@RequestBody Teacher teacher) {
//        return teacherService.addTeacher(teacher);
//    }

}
