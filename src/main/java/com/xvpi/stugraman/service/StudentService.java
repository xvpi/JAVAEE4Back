package com.xvpi.stugraman.service;

import com.xvpi.stugraman.DAO.TeacherDAO;
import com.xvpi.stugraman.beans.*;
import com.xvpi.stugraman.beans.Class;
import com.xvpi.stugraman.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class StudentService {
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
    private ClassMapper classMapper;
    @Autowired
    private CourseMapper courseMapper;

    private final TeacherDAO teacherDAO = new TeacherDAO();
    public Result getAllTeachers() {
        Result res = new Result();
        try{
            System.out.println("Get all teachers attemp");

            List<Teacher>  teachers = teacherDAO.getAll();
            if(teachers == null){
                res.setStatus(false);
                res.setResult("所有学生查询结果为空！");
                res.setTotal(0);  // 如果查询结果为空，total 设置为 0
                System.out.println("查询结果为空，请检查！");
                return res;
            }
            res.setStatus(true);
            res.setResult(teachers);
            res.setTotal(teachers.size());
            return res;
        } catch (DataAccessException e){
            e.printStackTrace();
            res.setStatus(false);
            res.setResult("异常: " + e.getMessage());
            res.setTotal(0);  // 发生异常时，total 设置为 0
            return res;
        }
    }
    public Result getTeacherByName(String name) {
        Result res = new Result();
        try{
            System.out.println("Get teacher by name attemp"+name);

            List<Teacher>  teachers = teacherMapper.getTeacherByName(name);
            if(teachers == null){
                res.setStatus(false);
                res.setResult("所有学生查询结果为空！");
                res.setTotal(0);  // 如果查询结果为空，total 设置为 0
                System.out.println("查询结果为空，请检查！");
                return res;
            }
            res.setStatus(true);
            res.setResult(teachers);
            res.setTotal(teachers.size());
            return res;
        } catch (DataAccessException e){
            e.printStackTrace();
            res.setStatus(false);
            res.setResult("异常: " + e.getMessage());
            res.setTotal(0);  // 发生异常时，total 设置为 0
            return res;
        }
    }

    public Result changeTitle(Teacher teacher) {

        Result res = new Result();
        try {
            String id=teacher.getId();
            String title =teacher.getTitle();
            System.out.println("Change title attemp:"+id+" "+title);
            boolean isUpdated = teacherMapper.updateTitle(id, title);
            if (isUpdated) {
                res.setStatus(true);
                res.setResult("学生专业更新成功");
            } else {
                res.setStatus(false);
                res.setResult("学生专业更新失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(false);
            res.setResult("异常: " + e.getMessage());
        }
        return res;
    }

    public Result deleteTeacherbyId(String id) {
        Result res = new Result();
        try {
            System.out.println("Delete Teacher attemp:"+id);

            int isTcDeleted = teacherMapper.deleteTeacherById(id);
            int isUsdDeleted = userMapper.deleteUserById(id);
            int isPsDeleted = personMapper.deletePersonById(id);

            if ( isTcDeleted>0 && isUsdDeleted>0 && isPsDeleted>0) {
                res.setStatus(true);
                res.setResult("学生删除成功");
            } else {
                res.setStatus(false);
                res.setResult("学生删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(false);
            res.setResult("异常: " + e.getMessage());
        }
        return res;
    }


    public Result addTeacher(Teacher teacher) {
        Result result = new Result();
        try {
            System.out.println("Create Teacher attemp");
            // 检查学生是否已经存在（例如，根据学号等唯一字段）
            if (teacherMapper.findById(teacher.getTeacherId())) {
                result.setStatus(false);
                result.setResult("该学生已存在");
                return result;
            }
            // 插入学生信息
            String id =teacher.getTeacherId();
            String title =teacher.getTitle();
            String name = teacher.getName();
            String gender =teacher.getGender();
            String password = "123456";//默认密码
            String role = "teacher";
            int prows = personMapper.addPerson(id,name,gender,role);
            int trows = teacherMapper.addTeacher(id,title,id);
            int urows = userMapper.addUser(id,name,password,role,id);
            if (prows > 0 && trows > 0 && urows > 0) {
                result.setStatus(true);
                result.setResult(teacher);
            } else {
                result.setStatus(false);
                result.setResult("添加学生失败");
            }
        } catch (Exception e) {
            result.setStatus(false);
            result.setResult("异常: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result getStudentById(String id) {
        Result res = new Result();
        try{
            System.out.println("Get student by id attemp"+id);
            Student  student = studentMapper.getStudentById(id);
            Person person = personMapper.getPersonById(id);
            User user = userMapper.getUserById(id);
            student.setPassword(user.getPasswordHash());
            student.setGender(person.getGender());
            student.setType("student");
            student.setName(person.getName());
            student.setId(person.getId());
            if(student == null){
                res.setStatus(false);
                res.setResult("学生查询结果为空！");
                res.setTotal(0);  // 如果查询结果为空，total 设置为 0
                System.out.println("查询结果为空，请检查！");
                return res;
            }
            res.setStatus(true);
            res.setResult(student);
            return res;
        } catch (DataAccessException e){
            e.printStackTrace();
            res.setStatus(false);
            res.setResult("异常: " + e.getMessage());
            return res;
        }
    }

    public Result changePassword(Student student) {
        Result res = new Result();
        try{
            String id= student.getId();
            String password = student.getPassword();
            System.out.println("Change student password attemp:"+id);
            int urows = userMapper.changePassword(id,password);
            if(urows <= 0){
                res.setStatus(false);
                res.setResult("修改密码失败！");
                System.out.println("修改密码失败，请检查！");
                return res;
            }
            res.setStatus(true);
            res.setResult("修改密码成功！"+password);
            return res;
        } catch (DataAccessException e){
            e.printStackTrace();
            res.setStatus(false);
            res.setResult("异常: " + e.getMessage());
            return res;
        }
    }

    public Result getClassesById(String id) {
        Result res = new Result();
        try{
            List<String> classIds = gradeMapper.getClassById(id);
            List<Class> classes = new ArrayList<>();
            for(String classId:classIds)
            {
                Class aclass = classMapper.getClassById(classId);
                // 如果班级信息为空，跳过当前循环
                if (aclass == null) {
                    continue;
                }

                // 获取并设置教师信息
                String teacherName = teacherMapper.getTeacherNameById(aclass.getTeacherId());
                if (teacherName != null) {
                    aclass.setTeacherName(teacherName);
                }

                // 获取并设置课程信息
                String courseName = courseMapper.getCourseNameById(aclass.getCourseId());
                if (courseName != null) {
                    aclass.setCourseName(courseName);
                }

                // 将班级信息添加到 classes 列表
                classes.add(aclass);
            }
            System.out.println("Get Classes attemp:"+id);
            if(classes == null){
                res.setStatus(false);
                res.setResult("获取教学班失败！");
                res.setTotal(0);  // 如果查询结果为空，total 设置为 0
                System.out.println("获取教学班失败，请检查！");
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
            return res;
        }
    }

}
