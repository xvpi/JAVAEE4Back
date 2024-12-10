package com.xvpi.stugraman.controller;
import com.xvpi.stugraman.DAO.*;
import com.xvpi.stugraman.beans.*;
import com.xvpi.stugraman.beans.Class;
import com.xvpi.stugraman.service.ClassService;
import com.xvpi.stugraman.service.CourseService;
import com.xvpi.stugraman.service.GradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/grade")
@Api(tags = "成绩相关端口")
public class GradeController {
    public final GradeService gradeService;
    @Autowired //将依赖关系自动注入到类中
    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @ApiOperation("获取成绩信息")
    @GetMapping("/getAllGrades")
    public Result getAllGrades() {
        return gradeService.getAllGrades();
    }
    @ApiOperation("获取学生对应的各科总成绩信息")
    @GetMapping("/getAllGradesByName")
    public Result getAllGradesByName(@RequestParam String name) {
        return gradeService.getAllGradesByName(name);
    }
    @ApiOperation("获取学生对应成绩信息")
    @GetMapping("/getGradesBySid")
    public Result getGradesBySid(@RequestParam String studentId) {
        return gradeService.getGradesBySid(studentId);
    }
    @ApiOperation("获取教学班对应成绩信息")
    @GetMapping("/getGradesByClassId")
    public Result getGradesByClassId(@RequestParam String classId) {
        return gradeService.getGradesByClassId(classId);
    }

    @ApiOperation("在已有学生中进行姓名查找")
    @GetMapping("/getGradesByNameId")
    public Result getGradesByNameId(@RequestParam("name") String name,@RequestParam("Id") String Id)
    {
        return gradeService.getGradesByNameId(name,Id);
    }
   @ApiOperation("更新成绩")
    @PostMapping("/saveStudentScores")
    public Result saveStudentScores(@RequestBody Grade grade) {
        return gradeService.saveStudentScores(grade);
    }


}
