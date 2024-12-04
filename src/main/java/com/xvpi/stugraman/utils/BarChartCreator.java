package com.xvpi.stugraman.utils;

import java.util.Random;

public class BarChartCreator {
    public static void createBarChart(int[] GradeDistribution) {
        // 用于显示总成绩柱形图
        System.out.println("总成绩柱形图:");
        StringBuilder bar = new StringBuilder();
        StringBuilder text = new StringBuilder();
        String ALL_PRINT = "█"; // 满的柱状
        Random backgroundRandom = new Random();
        for (int i = 0; i < GradeDistribution.length; i++) {
            // 根据人数生成柱状图
            int length = GradeDistribution[i]; // 人数
            text.append(String.format("%03d-%03d分: ", (i * 75 + 1), (i * 75 + 75)));

            for (int j = 0; j < length; j++) {
                bar.append(ALL_PRINT);
            }
            int background = 31 + i;
            System.out.format("%s\33[%dm%s\33[0m %n", text, background, bar);// %n是换行
            //System.out.format("%s\33[%d;%dm%s\33[0m %n", "--", 41, 1, bar);

            text.setLength(0); // 清空
            bar.setLength(0); // 清空
        }
    }
}
