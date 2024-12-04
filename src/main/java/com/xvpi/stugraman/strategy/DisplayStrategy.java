package com.xvpi.stugraman.strategy;

import com.xvpi.stugraman.DAO.CourseDAO;
import com.xvpi.stugraman.entity.Class;
import com.xvpi.stugraman.entity.*;
import com.xvpi.stugraman.utils.BarChartCreator;
import com.xvpi.stugraman.utils.TablePrinter;

import java.util.*;

import static com.xvpi.stugraman.StuGraManApplication.*;
import static com.xvpi.stugraman.strategy.GetCourseName.getCourseName;

public interface DisplayStrategy<T> {
    void display(T data);

    class DisplayAllData implements DisplayStrategy<DisplayData> {
        @Override
        public void display(DisplayData data) {
            // 学生信息
            System.out.println("学生列表:");
            List<Student> students = studentDAO.getAll();
            for (Student student : students) {
                System.out.println(student);
            }

            // 教师信息
            System.out.println("教师列表:");
            List<Teacher> teachers = teacherDAO.getAll();
            for (Teacher teacher : teachers) {
                System.out.println(teacher);
            }

            // 课程信息
            System.out.println("课程列表:");
            List<Course> courses = new CourseDAO().getAllCourses();
            for (Course course : courses) {
                System.out.println("课程编号为: " + course.getCourseId() + " 课程名称: " + course.getCourseName());
            }

            // 教学班信息
            System.out.println("教学班列表:");
            List<Class> classes = classDAO.getAllClasses();
            for (Class cl : classes) {
                System.out.println(cl);
            }
        }
    }

    class DisplayStudentSelection implements DisplayStrategy<DisplayData> {
        @Override
        public void display(DisplayData data) {
            // 展示所有教学班和学生信息
            List<Class> classes = classDAO.getAllClasses();
            for (Class cl : classes) {
                System.out.println("教学班号为：" + cl.getClassId() + " 教学班名为：" + cl.getClassName());
                List<Student> students = studentDAO.getStudentsByClassId(cl.getClassId());
                System.out.println("学生列表:");
                for (Student student : students) {
                    System.out.println(student);
                }
            }
        }
    }

    class DisplayGradeDistribution implements DisplayStrategy <DisplayData>{
        @Override
        public void display(DisplayData data) {
            List<Grade> grades = gradeDAO.getAllGrades();
            // 用于存储每个学生的总成绩
            Map<String, Integer> studentTotalScores = new HashMap<>();
            // 统计每个科目的分数段
            Map<String, int[]> subjectGradeDistributions = new HashMap<>();

            // 统计每个学生的总成绩和每个科目的成绩分段
            for (Grade grade : grades) {
                String studentId = grade.getStudentId();
                String classId = grade.getClassId();
                String courseName = getCourseName(classId); // 获取课程名称

                // 计算每个学生的总成绩
                studentTotalScores.put(studentId, studentTotalScores.getOrDefault(studentId, 0) + grade.getTotalScore());

                // 初始化课程对应的分段数组
                subjectGradeDistributions.putIfAbsent(courseName, new int[4]);

                // 统计该科目的成绩分段
                int totalScore = grade.getTotalScore();
                if (totalScore >= 1 && totalScore <= 25) {
                    subjectGradeDistributions.get(courseName)[0]++;
                } else if (totalScore >= 26 && totalScore <= 50) {
                    subjectGradeDistributions.get(courseName)[1]++;
                } else if (totalScore >= 51 && totalScore <= 75) {
                    subjectGradeDistributions.get(courseName)[2]++;
                } else if (totalScore >= 76 && totalScore <= 100) {
                    subjectGradeDistributions.get(courseName)[3]++;
                }
            }

            // 输出每个科目的成绩分段统计结果
            for (Map.Entry<String, int[]> entry : subjectGradeDistributions.entrySet()) {
                String courseName = entry.getKey();
                int[] distribution = entry.getValue();
                System.out.println(courseName + " 成绩分段统计表如下：");
                System.out.println("001-025分: " + distribution[0]);
                System.out.println("026-050分: " + distribution[1]);
                System.out.println("051-075分: " + distribution[2]);
                System.out.println("076-100分: " + distribution[3]);
                System.out.println();
            }

            // 计算学生的各科总成绩的分段统计
            int[] overallGradeDistribution = new int[4]; // 总成绩分段统计数组

            for (Integer totalScore : studentTotalScores.values()) {
                if (totalScore >= 1 && totalScore <= 75) {
                    overallGradeDistribution[0]++;
                } else if (totalScore >= 76 && totalScore <= 150) {
                    overallGradeDistribution[1]++;
                } else if (totalScore >= 151 && totalScore <= 225) {
                    overallGradeDistribution[2]++;
                } else if (totalScore >= 226 && totalScore <= 300) {
                    overallGradeDistribution[3]++;
                }
            }

            // 输出总成绩分段统计结果
            System.out.println("总成绩分段统计:");
            System.out.println("001-075分: " + overallGradeDistribution[0]);
            System.out.println("076-150分: " + overallGradeDistribution[1]);
            System.out.println("151-225分: " + overallGradeDistribution[2]);
            System.out.println("226-300分: " + overallGradeDistribution[3]);

            // 生成总成绩柱形图
            BarChartCreator.createBarChart(overallGradeDistribution);
        }
    }

    class DisplayClassGrades implements DisplayStrategy <DisplayData>{
        @Override
        public void display(DisplayData data) {
            List<Grade> grades = data.getGrades();
            List<String[]> tableData = new ArrayList<>();
            // 添加表头
            String[] headers = {" student_id ", "Reg", "Mid", "Lab", "Fin", "Tot","Grade_date"};
            tableData.add(headers);

            for (Grade grade : grades) {
                String[] rowData = {
                        grade.getStudentId(),
                        String.format("%02d", grade.getRegularScore()),
                        String.format("%02d", grade.getMidtermScore()),
                        String.format("%02d", grade.getLabScore()),
                        String.format("%02d", grade.getFinalScore()),
                        String.format("%02d", grade.getTotalScore()),
                        String.format("%tF",grade.getGradeDate())
                };
                tableData.add(rowData);
            }

            // 打印表格
            TablePrinter.printTable(tableData);
        }
    }

    class DisplayStudentGrades implements DisplayStrategy <DisplayData>{
        @Override
        public void display(DisplayData data) {
            String studentId = data.getStudentId();
            List<Grade> grades = gradeDAO.getGradesByStudentId(studentId);
            List<String[]> tableData = new ArrayList<>();

            // 添加表头
            String[] headers = {"CL_id", "Co_name", "Reg", "Mid", "Lab", "Fin", "Tot","Grade_date"};
            tableData.add(headers);
            int totalGrade = 0;

            // 添加每一行的数据
            for (Grade grade : grades) {
                Class classInfo = classDAO.getClassById(grade.getClassId());
                if (classInfo != null) {
                    Course courseInfo = CourseDAO.getCourseById(classInfo.getCourseId());
                    String courseName = (courseInfo != null) ? courseInfo.getCourseName() : "未知课程";
                    totalGrade += grade.getTotalScore();
                    String[] rowData = {
                            grade.getClassId(),
                            courseName,
                            String.format("%02d", grade.getRegularScore()),  // 格式化为两位数
                            String.format("%02d", grade.getMidtermScore()),  // 格式化为两位数
                            String.format("%02d", grade.getLabScore()),      // 格式化为两位数
                            String.format("%02d", grade.getFinalScore()),    // 格式化为两位数
                            String.format("%02d", grade.getTotalScore()),     // 格式化为两位数
                            String.format("%tF",grade.getGradeDate())
                    };
                    tableData.add(rowData);
                } else {
                    System.out.println("教学班信息未找到。");
                }

            }
            System.out.println("学生总成绩为：" + totalGrade);
            // 打印表格
            TablePrinter.printTable(tableData);
        }
    }

    class DisplayClassSortingOptions implements DisplayStrategy<DisplayData> {
        @Override
        public void display(DisplayData data) {
            Class cl = data.getCl();
            while (true) {
                System.out.println("请选择排序方式：");
                System.out.println("1. 按学号升序排列");
                System.out.println("2. 按成绩降序排列");
                System.out.println("3. 按成绩升序排列");
                System.out.println("4. 返回上级菜单");

                int choice = scanner.nextInt();
                Context<DisplayData> context = new Context<DisplayData>();
                Context<Grade> context1 = new Context<Grade>();

                switch (choice) {
                    case 1:
                        context1.setSortingStrategy(new SortingStrategy.SortByIdAscending());
                        break;
                    case 2:
                        context1.setSortingStrategy(new SortingStrategy.SortByGradeDscending());
                        break;
                    case 3:
                        context1.setSortingStrategy(new SortingStrategy.SortByGradeAscending());
                        break;
                    case 4:
                        return; // 返回上级菜单
                    default:
                        System.out.println("无效的选项，请重新输入。");
                }

                // 获取班级的成绩列表
                List<Grade> grades = gradeDAO.getGradesByClassId(cl.getClassId());
                grades = context1.executeSorting(grades); // 使用策略进行排序

                // 显示成绩
                context.setDisplayStrategy(new DisplayStrategy.DisplayClassGrades());
                context.executeDisplay(new DisplayData(grades));
            }
        }
    }


    //全体成绩表展示
    class DisplaySummaryGrades implements DisplayStrategy<DisplayDataForSummary> {
        @Override
        public void display(DisplayDataForSummary data) {
            List<Map<String, Object>> studentGradesSummary = data.getStudentGradesSummary(); // 从传入数据获取成绩摘要

            // 构建表头
            List<String[]> tableData = new ArrayList<>();
            Set<String> courseNames = new HashSet<>(); // 用于存储所有课程名

            // 首先提取所有课程名称以构建表头
            for (Map<String, Object> summary : studentGradesSummary) {
                Map<String, Integer> scores = (Map<String, Integer>) summary.get("scores");
                courseNames.addAll(scores.keySet());
            }

            // 将学生ID和姓名添加到表头
            List<String> headerList = new ArrayList<>();
            headerList.add("index");
            headerList.add("student_id");
            headerList.add("student_name");
            headerList.addAll(courseNames); // 动态添加课程名
            headerList.add("total");

            // 将表头转换为数组并添加到表格数据
            tableData.add(headerList.toArray(new String[0]));
            int index = 1;
            // 构建表格内容
            for (Map<String, Object> summary : studentGradesSummary) {
                String[] row = new String[headerList.size()]; // 根据动态表头列数创建行
                row[0] = String.valueOf(index++);
                row[1] = (String) summary.get("studentId");
                row[2] = (String) summary.get("studentName");

                // 填充各科成绩
                Map<String, Integer> scores = (Map<String, Integer>) summary.get("scores");
                for (int i = 3; i < headerList.size() - 1; i++) {
                    String courseName = headerList.get(i);
                    row[i] = String.format("%02d", scores.getOrDefault(courseName, 0)); // 两位数，前导零
                }

                // 添加总成绩
                row[row.length - 1] = String.format("%03d", summary.get("totalGrade"));

                // 将行数据添加到表格数据中
                tableData.add(row);
            }

            // 打印表格
            TablePrinter.printTable(tableData);
        }
    }


    //全体成绩表查询菜单

 //排序菜单界面
    class DisplayGradesMenu implements DisplayStrategy<DisplayDataForSummary> {
        @Override
        public void display(DisplayDataForSummary data) {
            while (true) {
                List<Map<String, Object>> studentGradesSummary = gradeDAO.getStudentGradesSummary();
                if (studentGradesSummary == null || studentGradesSummary.isEmpty()) {
                    System.out.println("没有学生成绩数据可显示。");
                    return; // 或者跳出循环，返回上级菜单
                }

                Set<String> courseNames = new HashSet<>(); // 用于存储课程名

                // 提取所有课程名称
                for (Map<String, Object> summary : studentGradesSummary) {
                    Map<String, Integer> scores = (Map<String, Integer>) summary.get("scores");
                    courseNames.addAll(scores.keySet());
                }

                System.out.println("请选择排序方式：");
                System.out.println("1. 按学号升序排列");
                System.out.println("2. 按学号降序排列");
                System.out.println("3. 按单科成绩升序排列");
                System.out.println("4. 按单科成绩降序排列");
                System.out.println("5. 按总成绩升序排列");
                System.out.println("6. 按总成绩降序排列");
                System.out.println("7. 返回上级菜单");

                int choice = scanner.nextInt();
                Context<Map<String, Object>> context = new Context<Map<String, Object>>();

                switch (choice) {
                    case 1:
                        context.setSortingStrategy(new SortingStrategy.SortByIdAscendingTotal());
                        break;
                    case 2:
                        context.setSortingStrategy(new SortingStrategy.SortByIdDescendingTotal());
                        break;
                    case 3:
                    case 4:
                        System.out.println("请输入要排序的科目名称（可选课程：" + courseNames + "）：");
                        scanner.nextLine(); // Consume the newline
                        String subjectName = scanner.nextLine();
                        if (!courseNames.contains(subjectName)) {
                            System.out.println("无效的科目名称，请重新输入。");
                            continue; // 返回循环继续
                        }
                        int subjectIndex = new ArrayList<>(courseNames).indexOf(subjectName);
                        if(choice == 3) context.setSortingStrategy(new SortingStrategy.SortBySubjectScoreAscending(subjectIndex));
                                else context.setSortingStrategy(new SortingStrategy.SortBySubjectScoreDscending(subjectIndex));
                        break;
                    case 5:
                        context.setSortingStrategy(new SortingStrategy.SortByTotalGradeAscending());
                        break;
                    case 6:
                        context.setSortingStrategy(new SortingStrategy.SortByTotalGradeDescending());
                        break;
                    case 7:
                        return; // 返回上级菜单
                    default:
                        System.out.println("无效的选项，请重新输入。");
                        continue; // 返回循环继续
                }

                // 处理策略为 null 的情况
                if (context == null) {
                    System.out.println("未选择有效的排序策略。");
                    continue; // 返回循环继续
                }

                // 根据选择的策略排序数据
                List<Map<String, Object>> sortedSummary = null;
                if (context != null) {
                    sortedSummary = context.executeSorting(studentGradesSummary);
                }

                // 打印表格
                DisplaySummaryGrades displayGrades = new DisplaySummaryGrades();
                displayGrades.display(new DisplayDataForSummary(sortedSummary));
            }
        }
    }


}


