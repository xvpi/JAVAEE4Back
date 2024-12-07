package com.xvpi.stugraman.strategy;

import com.xvpi.stugraman.DAO.*;
import com.xvpi.stugraman.beans.*;
import com.xvpi.stugraman.beans.Class;

import java.sql.Date;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DataInitializer {
    private final StudentDAO studentDAO = new StudentDAO();
    private final TeacherDAO teacherDAO = new TeacherDAO();
    private final UserDAO userDAO = new UserDAO();
    private final ClassDAO classDAO = new ClassDAO();
    private final CourseDAO courseDAO = new CourseDAO();
    private final GradeDAO gradeDAO = new GradeDAO();
    private final Random random = new Random();

    // 姓氏库
    private final String familyName1 =
            "赵钱孙李周吴郑王冯陈褚卫蒋沈韩杨朱秦尤许何吕施张孔曹严华金魏陶姜戚谢邹喻水云苏潘葛奚范彭郎鲁韦昌马苗凤花方俞任袁柳鲍史唐费岑薛雷贺倪汤滕殷罗毕郝邬安常乐于时傅卞齐康伍余元卜顾孟平";
    // 男生名字
    private final String boyName = "伟刚勇毅俊峰强军平保东文辉力明永健世广志义兴良海山仁波宁贵福生龙元全国胜学祥才发武新利清飞彬富顺信子杰涛昌成康星光天达安岩中茂进林有坚和彪博诚先敬震振壮会思群豪心邦承乐绍功松善厚庆磊民友裕河哲江超浩亮政谦亨奇固之轮翰朗伯宏言若鸣朋斌梁栋维启克伦翔旭鹏泽晨辰士以建家致树炎德行时泰盛雄琛钧冠策腾楠榕风航弘";
    // 女生名字
    private final String girlName = "秀娟英华慧巧美娜静淑惠珠翠雅芝玉萍红娥玲芬芳燕彩春菊兰凤洁梅琳素云莲真环雪荣爱妹霞香月莺媛艳瑞凡佳嘉琼勤珍贞莉桂娣叶璧璐娅琦晶妍茜秋珊莎锦黛青倩婷姣婉娴瑾颖露瑶怡婵雁蓓纨仪荷丹蓉眉君琴蕊薇菁梦岚苑婕馨瑗琰韵融园艺咏卿聪澜纯毓悦昭冰爽琬茗羽希宁欣飘育滢馥筠柔竹霭凝晓欢霄枫芸菲寒伊亚宜可姬舒影荔枝思丽";
    // 课程名称库
    private final String[] courseNames = {"计算机网络", "操作系统", "计算机组成原理", "数据结构", "马克思主义原理", "软件工程", "Java企业开发"};
    // 专业库
    private final String[] majors = {"计算机", "软件工程", "电气", "自动化", "通信", "电子"};

    // 职称库
    private final String[] titles = {"教授", "副教授", "讲师"};

    private String generateRandomName(String gender) {
        // 随机选择一个姓氏
        String familyName = String.valueOf(familyName1.charAt(random.nextInt(familyName1.length())));

        // 根据性别随机生成名字，长度为1到2个字符
        String name;
        if (gender == "女") {
            // 女孩名字
            int startIdx = random.nextInt(girlName.length());  // 生成合法的开始索引
            int length = random.nextBoolean() ? 1 : 2;         // 名字长度随机为1或2个字符
            int endIdx = Math.min(startIdx + length, girlName.length());  // 确保结束索引不超过长度
            name = girlName.substring(startIdx, endIdx);
        } else {
            // 男孩名字
            int startIdx = random.nextInt(boyName.length());
            int length = random.nextBoolean() ? 1 : 2;
            int endIdx = Math.min(startIdx + length, boyName.length());
            name = boyName.substring(startIdx, endIdx);
        }

        return familyName + name;
    }


    public void initializeData() {
        clearDatabase();
        generateEntities(100, studentDAO::insert, this::createStudent);
        generateEntities(6, teacherDAO::insert, this::createTeacher);
        generateEntities(3, courseDAO::insertCourse, this::createCourse);
        generateClasses(2);
    }

    private void clearDatabase() {
        gradeDAO.deleteAllGrades();
        classDAO.deleteAllClasses();
        userDAO.deleteAllUsers();
        studentDAO.deleteAll();
        teacherDAO.deleteAll();
        courseDAO.deleteAllCourses();
    }

    private <T> void generateEntities(int count, Consumer<T> insertMethod, IntFunction<T> entityCreator) {
        IntStream.range(0, count)
                .mapToObj(entityCreator)
                .forEach(insertMethod);
    }


    private Student createStudent(int i) {
        String studentId = "2024" + String.format("%04d", i);
        String gender = random.nextBoolean() ? "男" : "女";
        String name = generateRandomName(gender);
        String major = majors[random.nextInt(majors.length)]; // 随机选择一个专业

        return new Student(studentId, name, gender, major); // 假设 Student 类有一个 major 属性
    }


    private Teacher createTeacher(int i) {
        String teacherId = "T" + String.format("%03d", i);
        String gender = random.nextBoolean() ? "男" : "女";
        String name = generateRandomName(gender);
        String title = titles[random.nextInt(titles.length)]; // 随机选择一个职称

        return new Teacher(teacherId, name, gender, title); // 假设 Teacher 类有一个 title 属性
    }

    private Course createCourse(int i) {
        String courseId = "C" + String.format("%03d", i);
        String name = courseNames[i % courseNames.length]; // 从固定课程名列表中选择
        return new Course(courseId, name);
    }

    public void generateClasses(int numClassesPerCourse) {
        List<Teacher> teachers = teacherDAO.getAll();
        List<Course> courses = courseDAO.getAllCourses();
        List<Student> students = studentDAO.getAll();

        if (teachers.isEmpty() || courses.isEmpty() || students.isEmpty()) {
            System.out.println("教师、课程或学生信息不足，无法生成教学班");
            return;
        }

        courses.forEach(course -> IntStream.range(0, numClassesPerCourse).forEach(j -> {
            String classId = String.format("CL%03d", random.nextInt(1000));
            String className = "Class_" + classId.substring(2);
            String semester = random.nextBoolean() ? "春" : "秋";
            Teacher teacher = teachers.get(random.nextInt(teachers.size()));

            List<Student> classStudents = selectStudentsForClass(course, students);
            Class teachingClass = new Class(classId, className, semester, teacher.getId(), course.getCourseId(), classStudents.size());
            classDAO.insertClass(teachingClass);
            initializeGrades(classStudents, classId);
        }));

        System.out.println(numClassesPerCourse * courses.size() + " 个教学班已随机生成并插入数据库。");
    }

    private List<Student> selectStudentsForClass(Course course, List<Student> students) {
        return students.stream()
                .filter(student -> !studentAlreadyInCourse(student, course.getCourseId()))
                .limit(50)
                .collect(Collectors.toList());
    }

    private void initializeGrades(List<Student> classStudents, String classId) {
        classStudents.forEach(student -> {
            int regularScore = 1 + random.nextInt(100);
            int midtermScore = 1 + random.nextInt(100);
            int labScore = 1 + random.nextInt(100);
            int finalScore = 1 + random.nextInt(100);
            int totalScore = (regularScore + midtermScore * 2 + labScore + finalScore * 6) / 10;
            Date randomDate = generateRandomDate();

            Grade grade = new Grade(student.getId(), classId, regularScore, midtermScore, labScore, finalScore, totalScore, randomDate);
            try {
                gradeDAO.insertGrade(grade);
            } catch (Exception e) {
                System.err.println("未知异常: " + e.getMessage());
            }
        });
    }

    private Date generateRandomDate() {
        long startMillis = Date.valueOf("2024-06-01").getTime();
        long endMillis = Date.valueOf("2024-06-30").getTime();
        long randomMillis = startMillis + (long) (random.nextDouble() * (endMillis - startMillis));
        return new Date(randomMillis);
    }

    private boolean studentAlreadyInCourse(Student student, String courseId) {
        return classDAO.getClassesByStudentId(student.getId()).stream()
                .anyMatch(cl -> cl.getCourseId().equals(courseId));
    }
}