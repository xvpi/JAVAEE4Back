package com.xvpi.stugraman.controller;
import com.xvpi.stugraman.DAO.*;
import com.xvpi.stugraman.beans.Result;
import com.xvpi.stugraman.beans.Student;
import com.xvpi.stugraman.service.PersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/web")
@Api(tags = "网页版端口")
public class WebController {
    public final PersonService personService;
    @Autowired //将依赖关系自动注入到类中
    public WebController(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping("/springboot")
    public String startSpringBoot() {
        return "Welcome to the world of Spring Boot!";
    }
    @ApiOperation(value = "用户登录接口")
    @GetMapping("/webLogin")
    public Result login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("role") String role) {
        return personService.login(username,password,role);
    }

    @ApiOperation(value = "用户注册接口")
    @GetMapping("/webRegister")
    public Result regeister(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        @RequestParam("role") String role,
                        @RequestParam("gender") String gender,
                        @RequestParam("title") String title,
                        @RequestParam("major") String major) {
        return personService.register(username,password,role,gender,title,major);
    }
    @ApiOperation("初始化学生数据")
    @GetMapping("/initialData")
    public Result initialData()
    {
        return personService.initialData();
    }

    @ApiOperation("获取对应学生信息")
    @GetMapping("/getAllStudents")
    public Result getAllStudents()
    {
        return personService.getAllStudents();
    }
    @ApiOperation("获取对应学生信息")
    @GetMapping("/getStuByName")
    public Result getStuByName(@RequestParam("name") String name)
    {
        return personService.getStuByName(name);
    }

    @ApiOperation("转专业操作")
    @PutMapping("/changeMajor")
    public Result changeMajor(@RequestBody Student student) {
        return personService.changMajor(student);
    }
    @ApiOperation("删除对应学生信息")
    @DeleteMapping("/stuDelete/{id}")
    public Result deleteStudentbyId(@PathVariable("id") String id) {
        return personService.deleteStudentbyId(id);
    }
    @ApiOperation("新增学生")
    @PostMapping("/addStudent")
    public Result addStudent(@RequestBody Student student) {
        return personService.addStudent(student);
    }

}
