package com.xvpi.stugraman.utils;

import java.util.List;

public class TablePrinter {
    public static void printTable(List<String[]> tableData) {
        int[] columnWidths = new int[tableData.get(0).length];

        // 计算每列的最大宽度
        for (String[] row : tableData) {
            for (int i = 0; i < row.length; i++) {
                columnWidths[i] = Math.max(columnWidths[i], row[i].length());
            }
        }

        // 打印表头分隔行
        printBorder(columnWidths);
        printRow(tableData.get(0), columnWidths);
        printBorder(columnWidths);

        // 打印表格的其他行
        for (int i = 1; i < tableData.size(); i++) {
            printRow(tableData.get(i), columnWidths);
        }
        printBorder(columnWidths);
    }

    private static void printRow(String[] row, int[] columnWidths) {
        StringBuilder sb = new StringBuilder();
        sb.append("|"); // 开始边界
        for (int i = 0; i < row.length; i++) {
            // 居中对齐，确保字符串在列宽内居中
            String paddedValue = String.format("%" + (columnWidths[i] + (columnWidths[i] % 2 == 0 ? 0 : 1)) + "s",
                    row[i]);
            int padding = (columnWidths[i] - row[i].length()) / 2; // 计算左右填充
            sb.append(" ").append(paddedValue).append(" "); // 加空格
            sb.append(repeatString(" ", Math.max(0, columnWidths[i] - paddedValue.trim().length()))); //
            // 使用repeatString填充剩余空格
            sb.append("|"); // 列分隔符
        }
        System.out.println(sb.toString());
    }

    private static void printBorder(int[] columnWidths) {
        StringBuilder border = new StringBuilder("+");
        for (int width : columnWidths) {
            border.append(repeatString("-", width + 2)); // 每列宽度加2用于边距
            border.append("+");
        }
        System.out.println(border.toString());
    }

    private static int getTotalWidth(int[] columnWidths) {
        int totalWidth = 0;
        for (int width : columnWidths) {
            totalWidth += width + 3; // 每列加上分隔符的宽度
        }
        return totalWidth - 1; // 减去最后一个分隔符
    }

    // 新增的方法用于重复字符串
    private static String repeatString(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

}
