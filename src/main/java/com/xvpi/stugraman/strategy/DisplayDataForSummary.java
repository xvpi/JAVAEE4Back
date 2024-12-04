package com.xvpi.stugraman.strategy;

import java.util.List;
import java.util.Map;

public class DisplayDataForSummary {
    private List<Map<String, Object>> studentGradesSummary;

    public DisplayDataForSummary(List<Map<String, Object>> studentGradesSummary) {
        this.studentGradesSummary = studentGradesSummary;
    }

    public List<Map<String, Object>> getStudentGradesSummary() {
        return studentGradesSummary; // 新增 getter
    }
}
