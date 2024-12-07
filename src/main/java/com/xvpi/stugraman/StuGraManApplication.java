package com.xvpi.stugraman;

import com.xvpi.stugraman.strategy.Context;
import com.xvpi.stugraman.strategy.DataInitializer;
import com.xvpi.stugraman.strategy.DisplayStrategy;
import com.xvpi.stugraman.strategy.SearchStrategy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.xvpi.stugraman.DAO.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import java.util.*;

@SpringBootApplication
@EntityScan("com.xvpi.beans")
public class StuGraManApplication {
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        SpringApplication.run(testApplication.class,args);
    }

//
//    public static void main(String[] args) {
//
//        System.out.println("欢迎使用学生成绩管理系统");
//        Context context = new Context();
//
//        // 提示用户初始化数据
//        System.out.println("请输入1,完成系统数据随机初始化");
//        int initOption = scanner.nextInt();
//        if (initOption == 1) {
//            System.out.println("开始初始化数据...");
//            DataInitializer initializer = new DataInitializer();
//            initializer.initializeData();
//            // 展示所有信息
//            context.setDisplayStrategy(new DisplayStrategy.DisplayAllData());
//            context.executeDisplay(null);
//        } else {
//            System.out.println("无效输入，系统将退出。");
//            return;
//        }
//
//        // 学生选课
//        System.out.println("请输入2,完成学生选课");
//        initOption = scanner.nextInt();
//        if (initOption == 2) {
//            System.out.println("开始学生选课...");
//
//            // 展示选课信息
//            context.setDisplayStrategy(new DisplayStrategy.DisplayStudentSelection());
//            context.executeDisplay(null);
//        } else {
//            System.out.println("无效输入，系统将退出。");
//            return;
//        }
//
//
//        // 进入主菜单
//        while (true) {
//            System.out.println("      主菜单     ");
//            System.out.println("请输入选择的功能：");
//            System.out.println("1. 教学班信息查询");
//            System.out.println("2. 学生信息查询");
//            System.out.println("3. 课程信息查询");
//            System.out.println("4. 教师信息查询");
//            System.out.println("5. 成绩统计");
//            System.out.println("0. 退出系统");
//            System.out.print("请输入: ");
//            int option = scanner.nextInt();
//
//            switch (option) {
//                case 1:
//                    context.setSearchStrategy(new SearchStrategy.SearchClass());
//                    context.executeSearch();
//                    break;
//                case 2:
//                    context.setSearchStrategy(new SearchStrategy.SearchStudent());
//                    context.executeSearch();
//                    break;
//                case 3:
//                    context.setSearchStrategy(new SearchStrategy.SearchCourse());
//                    context.executeSearch();
//                    break;
//                case 4:
//                    context.setSearchStrategy(new SearchStrategy.SearchTeacher());
//                    context.executeSearch();
//                    break;
//                case 5:
//                    context.setDisplayStrategy(new DisplayStrategy.DisplayGradeDistribution());
//                    context.executeDisplay(null);
//                    break;
//                case 0:
//                    System.out.println("退出系统。");
//                    System.exit(0);
//                default:
//                    System.out.println("无效的选项，请重新输入。");
//            }
//        }
//    }
}
