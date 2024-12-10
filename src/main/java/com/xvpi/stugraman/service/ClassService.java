package com.xvpi.stugraman.service;

import com.xvpi.stugraman.DAO.StudentDAO;
import com.xvpi.stugraman.beans.Class;
import com.xvpi.stugraman.beans.*;
import com.xvpi.stugraman.mapper.*;
import com.xvpi.stugraman.strategy.DataInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class ClassService {
    @Autowired
    private PersonMapper personMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private GradeMapper gradeMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ClassMapper classMapper;
    // 查询所有教学班及其对应的授课老师
    public Result getAllClasses() {
        Result res = new Result();
        //List<Student> students = studentDAO.getAll();
        try{
            System.out.println("Get all classes attemp");
            List<Class> classes = classMapper.selectAllClasses();
            for (Class aclass : classes) {
                // 查询授课老师
                String teacherName = teacherMapper.getTeacherNameById(aclass.getTeacherId());
                String courseName = courseMapper.getCourseNameById(aclass.getCourseId());
                aclass.setTeacherName(teacherName);
                aclass.setCourseName(courseName);
            }
            if(classes == null){
                res.setStatus(false);
                res.setResult("所有教学班查询结果为空！");
                res.setTotal(0);  // 如果查询结果为空，total 设置为 0
                System.out.println("查询结果为空，请检查！");
                return res;
            }
            res.setStatus(true);
            res.setResult(classes);
            res.setTotal(classes.size());
            return res;
        } catch (DataAccessException e){
            e.printStackTrace();
            res.setStatus(false);
            res.setResult("异常: " + e.getMessage());
            res.setTotal(0);  // 发生异常时，total 设置为 0
            return res;
        }
    }

    // 新增教学班
    public Result addClass(Class aclass) {

        Result result = new Result();
        try {
            System.out.println("Create Class attemp");
            // 检查学生是否已经存在（例如，根据学号等唯一字段）
            if (classMapper.findById(aclass.getClassId())) {
                result.setStatus(false);
                result.setResult("该教学班已存在");
                return result;
            }
            if (!teacherMapper.findById(aclass.getTeacherId())) {
                result.setStatus(false);
                result.setResult("该老师不存在");
                return result;
            }
            if (!courseMapper.findById(aclass.getCourseId())) {
                result.setStatus(false);
                result.setResult("该课程不存在");
                return result;
            }

            int rowsAffected = classMapper.insertClass(aclass);

            if (rowsAffected > 0) {
                result.setStatus(true);
                result.setResult(aclass);
            } else {
                result.setStatus(false);
                result.setResult("添加教学班失败");
            }
        } catch (Exception e) {
            result.setStatus(false);
            result.setResult("异常: " + e.getMessage());
            e.printStackTrace();
        }
        return result;

    }

    // 删除教学班
    public Result deleteClass(String classId) {
        Result res = new Result();
        try {
            System.out.println("Delete Class attemp:"+classId);
            int rowsAffectedg = gradeMapper.deleteGradeById(classId);
            int rowsAffectedcl = classMapper.deleteClassById(classId);
            if ( rowsAffectedcl > 0) {
                res.setStatus(true);
                res.setResult("教学班删除成功");
            } else {
                res.setStatus(false);
                res.setResult("教学班删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(false);
            res.setResult("异常: " + e.getMessage());
        }
        return res;
    }

    // 按教学班名称搜索教学班
    public Result searchClassByName(String courseName) {
        Result res = new Result();
        try{
            System.out.println("Get class by courseName attemp"+courseName);
            String Id = courseMapper.findIdByName(courseName);//得到课程id(课程打全不然会报错)
            List<Class>  classes = classMapper.findClassByCourseId(Id);
            if(classes == null){
                res.setStatus(false);
                res.setResult("所有教学班查询结果为空！");
                res.setTotal(0);  // 如果查询结果为空，total 设置为 0
                System.out.println("查询结果为空，请检查！");
                return res;
            }
            for(Class aclass:classes){
                String tName = teacherMapper.getTeacherNameById(aclass.getTeacherId());
                String cName = courseMapper.getCourseNameById(aclass.getCourseId());
                aclass.setTeacherName(tName);
                aclass.setCourseName(cName);
            }
            res.setStatus(true);
            res.setResult(classes);
            res.setTotal(classes.size());
            return res;
        } catch (DataAccessException e){
            e.printStackTrace();
            res.setStatus(false);
            res.setResult("异常: " + e.getMessage());
            res.setTotal(0);  // 发生异常时，total 设置为 0
            return res;
        }
    }

    public Result getStudentsByClassId(String id) {
        Result res = new Result();
        //List<Student> students = studentDAO.getAll();
        try{
            System.out.println("Get students by class id attemp:"+id);

            List<String> studentIds = classMapper.getStudentsByClassId(id);
            List<Student> students = new ArrayList<>();
            for (String studentId : studentIds) {
                // 查询授课老师
                Student student = studentMapper.getStudentById(studentId);
                Person person = personMapper.getPersonById(studentId);
                User user = userMapper.getUserById(studentId);
                student.setPassword(user.getPasswordHash());
                student.setGender(person.getGender());
                student.setType("student");
                student.setName(person.getName());
                student.setId(person.getId());
                students.add(student);
            }
            if(students == null){
                res.setStatus(false);
                res.setResult("教学班下学生查询结果为空！");
                res.setTotal(0);  // 如果查询结果为空，total 设置为 0
                System.out.println("查询结果为空，请检查！");
                return res;
            }
            res.setStatus(true);
            res.setResult(students);
            res.setTotal(students.size());
            return res;
        } catch (DataAccessException e){
            e.printStackTrace();
            res.setStatus(false);
            res.setResult("异常: " + e.getMessage());
            res.setTotal(0);  // 发生异常时，total 设置为 0
            return res;
        }
    }

    public Result findByClassId(String classId) {
        Result res = new Result();
        try{
            System.out.println("find class by id attemp"+ classId);
            Class aclass = classMapper.getClassById(classId);//得到课程id(课程打全不然会报错)
            if(aclass == null){
                res.setStatus(false);
                res.setResult("教学班查询结果为空！");
                res.setTotal(0);  // 如果查询结果为空，total 设置为 0
                System.out.println("查询结果为空，请检查！");
                return res;
            }
                String tName = teacherMapper.getTeacherNameById(aclass.getTeacherId());
                String cName = courseMapper.getCourseNameById(aclass.getCourseId());
                aclass.setTeacherName(tName);
                aclass.setCourseName(cName);
            res.setStatus(true);
            res.setResult(aclass);
            return res;
        } catch (DataAccessException e){
            e.printStackTrace();
            res.setStatus(false);
            res.setResult("异常: " + e.getMessage());
            return res;
        }
    }

    public Result getStuByNameId(String name, String id) {
        Result res = new Result();
        try{
            System.out.println("在已有学生中进行姓名查找 attemp"+ name);
            List<String> studentIds = classMapper.getStudentsByClassId(id);
            List<Student> students = new ArrayList<>();
            for(String studentId : studentIds){
                // 查询授课老师
                Student student = studentMapper.getStudentById(studentId);
                Person person = personMapper.getPersonById(studentId);
               // System.out.println(student.toString());
                if(Objects.equals(person.getName(), name)){
                    User user = userMapper.getUserById(studentId);
                    student.setPassword(user.getPasswordHash());
                    student.setGender(person.getGender());
                    student.setType("student");
                    student.setName(person.getName());
                    student.setId(person.getId());
                    students.add(student);
                }
            }
            if(students.size() == 0){
                res.setStatus(false);
                res.setResult("学生查询结果为空！");
                res.setTotal(0);  // 如果查询结果为空，total 设置为 0
                System.out.println("查询结果为空，请检查！");
                return res;
            }
            res.setStatus(true);
            res.setResult(students);
            res.setTotal(students.size());
            return res;
        } catch (DataAccessException e){
            e.printStackTrace();
            res.setStatus(false);
            res.setResult("异常: " + e.getMessage());
            return res;
        }
    }
}
