package com.xvpi.stugraman.service;

import com.xvpi.stugraman.DAO.GradeDAO;
import com.xvpi.stugraman.DAO.StudentDAO;
import com.xvpi.stugraman.beans.Class;
import com.xvpi.stugraman.beans.*;
import com.xvpi.stugraman.mapper.*;
import com.xvpi.stugraman.strategy.DataInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GradeService {
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
    private final GradeDAO gradeDAO = new GradeDAO();
    public Result getAllGrades() {
        Result res = new Result();
        try{
            System.out.println("Get all grades attemp");
            List<Map<String, Object>>  list = gradeDAO.getStudentGradesSummary();
            if(list == null){
                res.setStatus(false);
                res.setResult("所有成绩查询结果为空！");
                res.setTotal(0);  // 如果查询结果为空，total 设置为 0
                System.out.println("查询结果为空，请检查！");
                return res;
            }
            res.setStatus(true);
            res.setResult(list);
            res.setTotal(list.size());
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

    public Result getGradesBySid(String sid) {
        Result res = new Result();
        try{
            System.out.println("Get grades by studentId attemp: "+sid);
            List<Grade>  grades = gradeMapper.findGradeByStudentId(sid);
            if(grades == null){
                res.setStatus(false);
                res.setResult("所有成绩查询结果为空！");
                res.setTotal(0);  // 如果查询结果为空，total 设置为 0
                System.out.println("查询结果为空，请检查！");
                return res;
            }
            for(Grade grade:grades)
            {
                String classId=grade.getClassId();
                String teacherId = classMapper.getTeacherIdById(classId);
                grade.setCourseName(classMapper.getClassNameById(classId));
                grade.setTeacherName(teacherMapper.getTeacherNameById(teacherId));
                Integer rank = gradeMapper.getRankById(sid,classId);
                System.out.println("排名:"+rank);
                grade.setRank(rank);
            }
            res.setStatus(true);
            res.setResult(grades);
            res.setTotal(grades.size());
            return res;
        } catch (DataAccessException e){
            e.printStackTrace();
            res.setStatus(false);
            res.setResult("异常: " + e.getMessage());
            res.setTotal(0);  // 发生异常时，total 设置为 0
            return res;
        }
    }

    public Result getGradesByClassId(String cid) {
        Result res = new Result();
        try{
            System.out.println("Get grades by classId attemp: "+cid);
            List<Grade>  grades = gradeMapper.findGradeByClassId(cid);
            if(grades == null){
                res.setStatus(false);
                res.setResult("所有成绩查询结果为空！");
                res.setTotal(0);  // 如果查询结果为空，total 设置为 0
                System.out.println("查询结果为空，请检查！");
                return res;
            }
            for(Grade grade:grades)
            {
                String sid = grade.getStudentId();
                String name = personMapper.getNameById(sid);
                String teacherId = classMapper.getTeacherIdById(cid);
                grade.setCourseName(classMapper.getClassNameById(cid));
                grade.setTeacherName(teacherMapper.getTeacherNameById(teacherId));
                Integer rank = gradeMapper.getRankById(sid,cid);
                //System.out.println("排名:"+rank);
                grade.setRank(rank);
                grade.setName(name);
            }
            res.setStatus(true);
            res.setResult(grades);
            res.setTotal(grades.size());
            return res;
        } catch (DataAccessException e){
            e.printStackTrace();
            res.setStatus(false);
            res.setResult("异常: " + e.getMessage());
            res.setTotal(0);  // 发生异常时，total 设置为 0
            return res;
        }
    }

    public Result getGradesByNameId(String name, String id) {
        Result res = new Result();
        try{
            System.out.println("在已有学生中进行姓名/学号查找 attemp"+ name);
            List<String> studentIds = classMapper.getStudentsByClassId(id);
            List<Grade> grades = new ArrayList<>();
            for(String studentId : studentIds){

                Grade grade = gradeMapper.findGradeBySidCid(studentId,id);

                Person person = personMapper.getPersonById(studentId);
                //System.out.println(person.toString());
                if(Objects.equals(person.getName(), name)){
                    grade.setName(person.getName());
                    Integer rank = gradeMapper.getRankById(studentId,id);
                    grade.setRank(rank);
                    grades.add(grade);
                }
                else if(Objects.equals(studentId, name)){
                    grade.setName(person.getName());
                    Integer rank = gradeMapper.getRankById(studentId,id);
                    grade.setRank(rank);
                    grades.add(grade);
                }
            }
            if(grades.size() == 0){
                res.setStatus(false);
                res.setResult("学生查询结果为空！");
                res.setTotal(0);  // 如果查询结果为空，total 设置为 0
                System.out.println("查询结果为空，请检查！");
                return res;
            }
            res.setStatus(true);
            res.setResult(grades);
            res.setTotal(grades.size());
            return res;
        } catch (DataAccessException e){
            e.printStackTrace();
            res.setStatus(false);
            res.setResult("异常: " + e.getMessage());
            return res;
        }
    }

    public Result saveStudentScores(Grade grade) {
        Result res = new Result();
        try {
            String sid=grade.getStudentId();
            String cid=grade.getClassId();
            System.out.println("Update grade attemp:"+sid+" "+cid);
            int rs = grade.getRegularScore();int ms =grade.getMidtermScore();
            int ls = grade.getLabScore();int fs = grade.getFinalScore();
            int ts = (rs + ms * 2 + ls + fs * 6) / 10;
            Date gd = grade.getGradeDate();
            grade.setTotalScore(ts);
            int rank = gradeMapper.getRankById(sid,cid);
            grade.setRank(rank);
            boolean isUpdated = gradeMapper.saveStudentScores(sid,cid,rs,ms,ls,fs,ts,gd);
            if (isUpdated) {
                res.setStatus(true);
                res.setResult("学生成绩更新成功");
            } else {
                res.setStatus(false);
                res.setResult("学生成绩更新失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(false);
            res.setResult("异常: " + e.getMessage());
        }
        return res;
    }

    public Result getAllGradesByName(String name) {
        Result res = new Result();
        try{
            System.out.println("Get all grades attemp");
            List<Map<String, Object>>  list = gradeDAO.getStudentGradesSummary();
            List<Map<String, Object>> ls = new ArrayList<>();
            if(list == null){
                res.setStatus(false);
                res.setResult("所有成绩查询结果为空！");
                res.setTotal(0);  // 如果查询结果为空，total 设置为 0
                System.out.println("查询结果为空，请检查！");
                return res;
            }
            else{
                for(Map<String, Object> m:list){
                    if(m.containsValue(name)) {
                        ls.add(m);
                        res.setStatus(true);
                        res.setResult(ls);
                        res.setTotal(ls.size());
                        return res;
                    }
                }
            }
            res.setStatus(false);
            res.setResult(list);
            res.setTotal(list.size());
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