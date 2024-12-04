package com.xvpi.stugraman.entity;

import java.sql.Date;

public class Grade {
    private String studentId;
    private String classId;
    private int regularScore;
    private int midtermScore;
    private int labScore;
    private int finalScore;
    private int totalScore;
    private Date gradeDate;

    public Grade() {}

    public Grade(String studentId, String classId, int regularScore, int midtermScore, int labScore, int finalScore, int totalScore, Date gradeDate) {
        this.studentId = studentId;
        this.classId = classId;
        this.regularScore = regularScore;
        this.midtermScore = midtermScore;
        this.labScore = labScore;
        this.finalScore = finalScore;
        this.totalScore = totalScore;
        this.gradeDate = gradeDate;
    }


    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public int getRegularScore() {
        return regularScore;
    }

    public void setRegularScore(int regularScore) {
        this.regularScore = regularScore;
    }

    public int getMidtermScore() {
        return midtermScore;
    }

    public void setMidtermScore(int midtermScore) {
        this.midtermScore = midtermScore;
    }

    public int getLabScore() {
        return labScore;
    }

    public void setLabScore(int labScore) {
        this.labScore = labScore;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public Date getGradeDate() {
        return gradeDate;
    }

    public void setGradeDate(Date gradeDate) {
        this.gradeDate = gradeDate;
    }

    @Override
    public String toString() {
        return "Grade [studentId=" + studentId + ", classId=" + classId + ", regularScore=" + regularScore
                + ", midtermScore=" + midtermScore + ", labScore=" + labScore + ", finalScore=" + finalScore
                + ", totalScore=" + totalScore + ", gradeDate=" + gradeDate + "]";
    }
}
