package com.xvpi.stugraman.service;

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
public class TeacherService {
    @Autowired
    private PersonMapper personMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private ClassMapper classMapper;
    @Autowired
    private GradeMapper gradeMapper;
    public Result getAllTeachers() {
        Result res = new Result();
        try{
            System.out.println("Get all teachers attemp");

            List<Teacher>  teachers = teacherMapper.getAllTeachers();
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
            System.out.println("Get teacher by name/id attemp"+name);

            List<Teacher>  teachers = teacherMapper.getTeacherByName(name);
            Teacher teacher = teacherMapper.getTeacherById(name);
            if(teacher!=null) teachers.add(teacher);
            if(teachers == null){
                res.setStatus(false);
                res.setResult("所有老师查询结果为空！");
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
            String id=teacher.getTeacherId();
            String title =teacher.getTitle();
            System.out.println("Change title attemp:"+id+" "+title);
            boolean isUpdated = teacherMapper.updateTitle(id, title);
            if (isUpdated) {
                res.setStatus(true);
                res.setResult("老师专业更新成功");
            } else {
                res.setStatus(false);
                res.setResult("老师专业更新失败");
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
                res.setResult("老师删除成功");
            } else {
                res.setStatus(false);
                res.setResult("老师删除失败");
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
            // 检查老师是否已经存在（例如，根据学号等唯一字段）
            if (teacherMapper.findById(teacher.getTeacherId())) {
                result.setStatus(false);
                result.setResult("该老师已存在");
                return result;
            }
            // 插入老师信息
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
                result.setResult("添加老师失败");
            }
        } catch (Exception e) {
            result.setStatus(false);
            result.setResult("异常: " + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    public Result getTeacherById(String id) {
        Result res = new Result();
        try{
            System.out.println("Get teacher by id attemp:"+id);
            Teacher  teacher = teacherMapper.getTeacherById(id);
            Person person = personMapper.getPersonById(id);
            User user = userMapper.getUserById(id);
            teacher.setPassword(user.getPasswordHash());
            teacher.setGender(person.getGender());
            teacher.setType("teacher");
            teacher.setName(person.getName());
            teacher.setId(person.getId());
            if(teacher == null){
                res.setStatus(false);
                res.setResult("老师查询结果为空！");
                System.out.println("查询结果为空，请检查！");
                return res;
            }
            res.setStatus(true);
            res.setResult(teacher);
            return res;
        } catch (DataAccessException e){
            e.printStackTrace();
            res.setStatus(false);
            res.setResult("异常: " + e.getMessage());
            res.setTotal(0);  // 发生异常时，total 设置为 0
            return res;
        }
    }

    public Result changePassword(Teacher teacher) {
        Result res = new Result();
        try{
            String id= teacher.getId();
            String password = teacher.getPassword();
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

    public Result getClassesByTeacherId(String id) {
        Result res = new Result();
        try{
            System.out.println("Get classes by teacher attemp"+id);
            List<String>  classIds = teacherMapper.getClassIdsByTeacherId(id);
            List<Class>  classes = new ArrayList<>();
            for(String classId: classIds){
                Class aclass = classMapper.getClassById(classId);
                aclass.setCourseName(courseMapper.getCourseNameById(aclass.getCourseId()));
                classes.add(aclass);
            }

            if(classes == null){
                res.setStatus(false);
                res.setResult("老师对应教学班查询结果为空！");
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
}
