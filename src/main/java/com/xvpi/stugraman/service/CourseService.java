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
public class CourseService {
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
    // 查询所有课程及其对应的授课老师
    public Result getAllCourses() {
        Result res = new Result();
        //List<Student> students = studentDAO.getAll();
        try{
            System.out.println("Get all courses attemp");

            List<Course>  courses = courseMapper.selectAllCourses();
            for (Course course : courses) {
                // 查询授课老师
                List<String> teacherNames = courseMapper.getTeacherNamesByCourseId(course.getCourseId());
                course.setTeacherNames(String.join(";", teacherNames));  // 将老师名字以分号分隔
            }
            if(courses == null){
                res.setStatus(false);
                res.setResult("所有课程查询结果为空！");
                res.setTotal(0);  // 如果查询结果为空，total 设置为 0
                System.out.println("查询结果为空，请检查！");
                return res;
            }
            res.setStatus(true);
            res.setResult(courses);
            res.setTotal(courses.size());
            return res;
        } catch (DataAccessException e){
            e.printStackTrace();
            res.setStatus(false);
            res.setResult("异常: " + e.getMessage());
            res.setTotal(0);  // 发生异常时，total 设置为 0
            return res;
        }
    }

    // 新增课程
    public Result addCourse(Course course) {

        Result result = new Result();
        try {
            System.out.println("Create Course attemp");
            // 检查学生是否已经存在（例如，根据学号等唯一字段）
            if (courseMapper.findById(course.getCourseId())) {
                result.setStatus(false);
                result.setResult("该课程已存在");
                return result;
            }
            int rowsAffected = courseMapper.insertCourse(course);

            if (rowsAffected > 0) {
                result.setStatus(true);
                result.setResult(course);
            } else {
                result.setStatus(false);
                result.setResult("添加课程失败");
            }
        } catch (Exception e) {
            result.setStatus(false);
            result.setResult("异常: " + e.getMessage());
            e.printStackTrace();
        }
        return result;

    }

    // 删除课程
    public Result deleteCourse(String courseId) {
        Result res = new Result();
        try {
            System.out.println("Delete Course attemp:"+courseId);
            List<String> classIds = classMapper.findClassIdByCourseId(courseId);
            int rowsAffectedcl=1,rowsAffectedg=1;
            for(String classId :classIds){
                rowsAffectedg = gradeMapper.deleteGradeById(classId);
                rowsAffectedcl = classMapper.deleteClassById(classId);
            }
            int rowsAffectedc = courseMapper.deleteCourse(courseId);
            if (rowsAffectedc > 0) {
                res.setStatus(true);
                res.setResult("课程删除成功");
            } else {
                res.setStatus(false);
                res.setResult("课程删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            res.setStatus(false);
            res.setResult("异常: " + e.getMessage());
        }
        return res;
    }

    // 按课程名称搜索课程
    public Result searchCourseByName(String courseName) {
        Result res = new Result();
        try{
            System.out.println("Get course by name attemp"+courseName);
            List<Course>  courses = courseMapper.selectCourseByName(courseName);
            if(courses == null){
                res.setStatus(false);
                res.setResult("所有课程查询结果为空！");
                res.setTotal(0);  // 如果查询结果为空，total 设置为 0
                System.out.println("查询结果为空，请检查！");
                return res;
            }
            res.setStatus(true);
            res.setResult(courses );
            res.setTotal(courses .size());
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
