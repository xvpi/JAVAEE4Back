package com.xvpi.stugraman.strategy;

import com.xvpi.stugraman.entity.Grade;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public interface SortingStrategy<T> {
    List<T> sort(List<T> data);
    class SortByIdAscending implements SortingStrategy<Grade> {
        @Override
        public List<Grade> sort(List<Grade> grades) {
            Collections.sort(grades, Comparator.comparing(Grade::getStudentId));
            return grades;
        }
    }

    class SortByIdDscending implements SortingStrategy<Grade> {
        @Override
        public List<Grade> sort(List<Grade> grades) {
            Collections.sort(grades, Comparator.comparing(Grade::getStudentId).reversed());
            return grades;
        }
    }

    class SortByGradeAscending implements SortingStrategy<Grade> {
        @Override
        public List<Grade> sort(List<Grade> grades) {
            Collections.sort(grades, Comparator.comparing(Grade::getTotalScore));
            return grades;
        }
    }

    class SortByGradeDscending implements SortingStrategy<Grade> {
        @Override
        public List<Grade> sort(List<Grade> grades) {
            Collections.sort(grades, Comparator.comparing(Grade::getTotalScore).reversed());
            return grades;
        }
    }
    class SortByIdAscendingTotal implements SortingStrategy<Map<String, Object>> {
        @Override
        public List<Map<String, Object>> sort(List<Map<String, Object>> studentGradesSummary) {
            Collections.sort(studentGradesSummary, Comparator.comparing(summary -> (String) summary.get("studentId")));
            return studentGradesSummary;
        }
    }

    class SortByIdDescendingTotal implements SortingStrategy<Map<String, Object>> {
        @Override
        public List<Map<String, Object>> sort(List<Map<String, Object>> studentGradesSummary) {
            studentGradesSummary.sort(new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> summary1, Map<String, Object> summary2) {
                    String id1 = (String) summary1.get("studentId");
                    String id2 = (String) summary2.get("studentId");
                    return id2.compareTo(id1);  // 降序比较
                }
            });
            return studentGradesSummary;
        }
    }

    class SortByTotalGradeDescending implements SortingStrategy<Map<String, Object>> {
        @Override
        public List<Map<String, Object>> sort(List<Map<String, Object>> studentGradesSummary) {
            studentGradesSummary.sort(new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> summary1, Map<String, Object> summary2) {
                    Integer totalGrade1 = (Integer) summary1.get("totalGrade");
                    Integer totalGrade2 = (Integer) summary2.get("totalGrade");
                    return totalGrade2.compareTo(totalGrade1);  // 降序比较
                }
            });
            return studentGradesSummary;
        }
    }


    class SortByTotalGradeAscending implements SortingStrategy<Map<String, Object>> {
        @Override
        public List<Map<String, Object>> sort(List<Map<String, Object>> studentGradesSummary) {
            Collections.sort(studentGradesSummary, Comparator.comparing(summary -> (Integer) summary.get("totalGrade")));
            return studentGradesSummary;
        }
    }


    class SortBySubjectScoreAscending implements SortingStrategy<Map<String, Object>> {
        private final int subjectIndex;

        public SortBySubjectScoreAscending(int subjectIndex) {
            this.subjectIndex = subjectIndex;
        }

        @Override
        public List<Map<String, Object>> sort(List<Map<String, Object>> studentGradesSummary) {
            Collections.sort(studentGradesSummary, Comparator.comparing(summary -> {
                // 强制转换到 Map<String, Integer>
                Map<String, Integer> scores = (Map<String, Integer>) summary.get("scores");
                if (scores == null || scores.isEmpty()) return 0;  // 检查是否为空

                // 获取科目名称
                String subjectName = (String) scores.keySet().toArray()[subjectIndex];
                return scores.getOrDefault(subjectName, 0);  // 获取分数，若没有则返回0
            }));
            return studentGradesSummary;
        }
    }

    //直接用reversed会报类型错误
    class SortBySubjectScoreDscending implements SortingStrategy<Map<String, Object>> {
        private final int subjectIndex;

        public SortBySubjectScoreDscending(int subjectIndex) {
            this.subjectIndex = subjectIndex;
        }

        @Override
        public List<Map<String, Object>> sort(List<Map<String, Object>> studentGradesSummary) {
            studentGradesSummary.sort(new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> summary1, Map<String, Object> summary2) {
                    Map<String, Integer> scores1 = (Map<String, Integer>) summary1.get("scores");
                    Map<String, Integer> scores2 = (Map<String, Integer>) summary2.get("scores");

                    int score1 = (scores1 != null && scores1.size() > subjectIndex)
                            ? scores1.getOrDefault((String) scores1.keySet().toArray()[subjectIndex], 0) : 0;

                    int score2 = (scores2 != null && scores2.size() > subjectIndex)
                            ? scores2.getOrDefault((String) scores2.keySet().toArray()[subjectIndex], 0) : 0;

                    return Integer.compare(score2, score1);  // 降序
                }
            });
            return studentGradesSummary;
        }
    }



}

