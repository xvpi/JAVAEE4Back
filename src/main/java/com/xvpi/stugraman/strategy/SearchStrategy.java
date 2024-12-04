package com.xvpi.stugraman.strategy;

import com.xvpi.stugraman.DAO.*;
import com.xvpi.stugraman.entity.Class;
import com.xvpi.stugraman.entity.Course;
import com.xvpi.stugraman.entity.Grade;
import com.xvpi.stugraman.entity.Student;
import com.xvpi.stugraman.entity.Teacher;
import com.xvpi.stugraman.utils.TablePrinter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public abstract class SearchStrategy {
    protected static final StudentDAO studentDAO = new StudentDAO();
    protected static final TeacherDAO teacherDAO = new TeacherDAO();
    protected static final ClassDAO classDAO = new ClassDAO();
    protected static final CourseDAO courseDAO = new CourseDAO();
    protected static final GradeDAO gradeDAO = new GradeDAO();

    public abstract void search();

    public static class SearchTeacher extends SearchStrategy {
        @Override
        public void search() {
            List<Teacher> teachers = teacherDAO.getAll();
            List<String[]> tableData = new ArrayList<>();
            String[] headers = {"teacherId", "name", "gender"};
            tableData.add(headers);

            for (Teacher teacher : teachers) {
                String[] rowData = {teacher.getId(), teacher.getName(), teacher.getGender()};
                tableData.add(rowData);
            }

            System.out.println("教师信息:");
            TablePrinter.printTable(tableData);
            System.out.println();
        }
    }

    public static class SearchCourse extends SearchStrategy {
        @Override
        public void search() {
            List<Course> courses = courseDAO.getAllCourses();

            for (Course course : courses) {
                System.out.println("课程编号: " + course.getCourseId() + ", 课程名称: " + course.getCourseName());
                List<Class> classes = classDAO.getClassesByCourseId(course.getCourseId());
                List<String[]> tableData = new ArrayList<>();
                String[] headers = {"classId", "className", "semester", "teacherId", "courseId", "totalStudents"};
                tableData.add(headers);

                for (Class cl : classes) {
                    String[] rowData = {
                            cl.getClassId(),
                            cl.getClassName(),
                            cl.getSemester(),
                            cl.getTeacherId(),
                            cl.getCourseId(),
                            String.valueOf(cl.getTotalStudents()),
                    };
                    tableData.add(rowData);
                }

                System.out.println("相关教学班信息:");
                TablePrinter.printTable(tableData);
                System.out.println();
            }
        }
    }

    public static class SearchClass extends SearchStrategy {
        private static final Pattern classIdPattern = Pattern.compile("^CL\\d{3}$");
        @Override
        public void search() {
            Scanner scanner = new Scanner(System.in);
            System.out.print("请输入想查询的教学班号如CL001: ");
            String classId = scanner.next();
            if (!classIdPattern.matcher(classId).matches()) {
                System.out.println("教学班号格式无效！请按规则输入（例如：CL001）。");
                return;
            }
            Class cl = classDAO.getClassById(classId);
            if (cl != null) {
                Course courseInfo = courseDAO.getCourseById(cl.getCourseId());
                String courseName = (courseInfo != null) ? courseInfo.getCourseName() : "未知课程";
                System.out.println("你查询的班级为: " + cl.getClassId() + " - " + cl.getClassName() + " - " + cl.getSemester() + " - " + cl.getTotalStudents() + " - " + courseName);
                List<Grade> grades = gradeDAO.getGradesByClassId(cl.getClassId());
                Context context = new Context();
                context.setDisplayStrategy(new DisplayStrategy.DisplayClassGrades());
                context.executeDisplay(new DisplayData(grades));
                context.setDisplayStrategy(new DisplayStrategy.DisplayClassSortingOptions());
                context.executeDisplay(new DisplayData(cl));
            } else {
                System.out.println("未找到该教学班。");
            }
        }
    }

    public static class SearchStudent extends SearchStrategy {
        private static final Pattern studentIdPattern = Pattern.compile("^2024\\d{4}$");
        @Override
        public void search() {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("请选择查询功能：");
                System.out.println("1. 按学号查询详细学生信息");
                System.out.println("2. 按姓名查询详细学生信息");
                System.out.println("3. 查询全体成绩表");
                System.out.println("4. 返回上级菜单");
                int choice = scanner.nextInt();
                Context context = new Context();
                switch (choice) {
                    case 1:
                        System.out.print("请输入学号: ");
                        String studentId = scanner.next();
                        if (!studentIdPattern.matcher(studentId).matches()) {
                            System.out.println("学号格式无效！请按规则输入（例如：20240001）。");
                            break;
                        }
                        Student student = studentDAO.getById(studentId);
                        if (student != null) {
                            System.out.println("学生信息: " + student);
                            context.setDisplayStrategy(new DisplayStrategy.DisplayStudentGrades());
                            context.executeDisplay(new DisplayData(student.getId()));
                        } else {
                            System.out.println("未找到该学生。");
                        }
                        break;
                    case 2:
                        System.out.print("请输入姓名: ");
                        String name = scanner.next();
                        List<Student> students = studentDAO.getByName(name);
                        if (!students.isEmpty()) {
                            for (Student s : students) {
                                System.out.println("学生信息: " + s);
                                context.setDisplayStrategy(new DisplayStrategy.DisplayStudentGrades());
                                context.executeDisplay(new DisplayData(s.getId()));
                            }
                        } else {
                            System.out.println("未找到该学生。");
                        }
                        break;
                    case 3:
                        System.out.println("所有学生的各科成绩与总成绩表如下：");
                        List<Map<String, Object>> studentGradesSummary = gradeDAO.getStudentGradesSummary();
                        DisplayDataForSummary displayData = new DisplayDataForSummary(studentGradesSummary);
                        context.setDisplayStrategy(new DisplayStrategy.DisplaySummaryGrades());
                        context.executeDisplay(displayData);
                        context.setDisplayStrategy(new DisplayStrategy.DisplayGradesMenu());
                        context.executeDisplay(null);
                        return;
                    case 4:
                        return;
                    default:
                        System.out.println("无效的选项，请重新输入。");
                }
            }
        }
    }
}
