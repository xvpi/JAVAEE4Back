package com.xvpi.stugraman.service;

import com.xvpi.stugraman.DAO.StudentDAO;
import com.xvpi.stugraman.beans.*;
import com.xvpi.stugraman.mapper.*;
import com.xvpi.stugraman.strategy.DataInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PersonService {
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
    public Result login(String username,String password,String role) {
        Result res = new Result();
        try{
            System.out.println("Login attempt with username: " + username + ", role: " + role);
            User user = userMapper.login(username, password,role);
            if(user == null){
                res.setStatus(false);
                res.setResult("账户或密码错误！");
                System.out.println("查询结果为空，账户或密码错误！");
                return res;
            }
            res.setStatus(true);
            res.setResult(user);
            return res;
        } catch (DataAccessException e){
            e.printStackTrace();
            res.setStatus(false);
            res.setResult("异常: " + e.getMessage());
            return res;
        }
    }
    public Result register(String username,String password,String role, String gender,String title,String major) {
        Result res = new Result();
        try{
            // 1. 插入到 person 表（生成 person.id）
            String personId = UUID.randomUUID().toString();
            Person person = new Person();
            person.setId(personId);
            person.setName(username);
            person.setGender(gender);
            person.setType(role);  // role: student, teacher, admin
            personMapper.insert(person);  // 插入 person 表
            // 2. 插入到 user 表
            User user = new User();
            user.setUserId(UUID.randomUUID().toString());
            user.setUsername(username);
            user.setPasswordHash(password); // 加密密码
            user.setRole(User.Role.valueOf(role));
            user.setPersonId(personId);  // 设置为刚刚插入的 person.id
            userMapper.insert(user);  // 插入 user 表

            // 3. 根据角色插入到对应的表
            if ("student".equals(role)) {
                // 注册学生
                Student student = new Student();
                student.setStudentId(UUID.randomUUID().toString());  // 学生ID
                student.setMajor(major);
                student.setPersonId(personId);  // 关联 person.id
                studentMapper.insert(student);  // 插入到 student 表
            } else if ("teacher".equals(role)) {
                // 注册教师
                Teacher teacher = new Teacher();
                teacher.setTeacherId(UUID.randomUUID().toString());  // 教师ID
                teacher.setTitle(title);
                teacher.setPersonId(personId);  // 关联 person.id
                teacherMapper.insert(teacher);  // 插入到 teacher 表
            }

            res.setStatus(true);
            res.setResult("注册成功！");
            return res;
        } catch (DataAccessException e){
            e.printStackTrace();
            res.setStatus(false);
            res.setResult("异常: " + e.getMessage());
            return res;
        }
    }

    public Result initialData() {
        System.out.println("开始初始化数据...");
        Result res = new Result();
        try{
            System.out.println("Data initializing attempt");
            DataInitializer initializer = new DataInitializer();
            initializer.initializeData();
            res.setStatus(true);
            res.setResult("初始化成功");
            return res;
        } catch (DataAccessException e){
            e.printStackTrace();
            res.setStatus(false);
            res.setResult("初始化异常: " + e.getMessage());
            return res;
        }

    }
    private final StudentDAO studentDAO = new StudentDAO();
    public Result getAllStudents() {
        Result res = new Result();
        //List<Student> students = studentDAO.getAll();
        try{
            System.out.println("Get all students attemp");

            List<Student>  students = studentDAO.getAll();
            if(students == null){
                res.setStatus(false);
                res.setResult("所有学生查询结果为空！");
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
    public Result getStuByName(String name) {
        Result res = new Result();
        //List<Student> students = studentDAO.getAll();
        try{
            System.out.println("Get student by name attemp"+name);

            List<Student>  students = studentMapper.getStuByName(name);
            if(students == null){
                res.setStatus(false);
                res.setResult("所有学生查询结果为空！");
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

    public Result changMajor(Student student) {

        Result res = new Result();
        try {
            String id=student.getId();
            String major =student.getMajor();
            System.out.println("Change major attemp:"+id+" "+major);
            boolean isUpdated = studentDAO.updateMajor(id, major);
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

    public Result deleteStudentbyId(String id) {
        Result res = new Result();
        try {
            System.out.println("Delete Student attemp:"+id);
            int isGdDeleted = gradeMapper.deleteGradeById(id);
            int isStuDeleted = studentMapper.deleteStudentById(id);
            int isUsdDeleted = userMapper.deleteUserById(id);
            int isPsDeleted = personMapper.deletePersonById(id);

            if (isGdDeleted>0 && isStuDeleted>0 && isUsdDeleted>0 && isPsDeleted>0) {
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


    public Result addStudent(Student student) {
        Result result = new Result();
        try {
            System.out.println("Create Student attemp");
            // 检查学生是否已经存在（例如，根据学号等唯一字段）
            if (studentMapper.findById(student.getStudentId())) {
                result.setStatus(false);
                result.setResult("该学生已存在");
                return result;
            }
            // 插入学生信息
            String id =student.getStudentId();
            String major =student.getMajor();
            String name = student.getName();
            String gender =student.getGender();
            String password = "123456";//默认密码
            String role = "student";
            int prows = personMapper.addPerson(id,name,gender,role);
            int srows = studentMapper.addStudent(id,major,id);
            int urows = userMapper.addUser(id,name,password,role,id);
            if (prows > 0 && srows > 0 && urows > 0) {
                result.setStatus(true);
                result.setResult(student);
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
}