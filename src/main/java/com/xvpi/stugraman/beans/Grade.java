package com.xvpi.stugraman.beans;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "grade")
public class Grade implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private GradeId gradeId;

    private Integer regularScore;
    private Integer midtermScore;
    private Integer labScore;
    private Integer finalScore;
    private Integer totalScore;
    private java.sql.Date gradeDate;
    public Grade(String studentId, String classId, int regularScore, int midtermScore, int labScore, int finalScore, int totalScore, Date gradeDate) {
        this.gradeId = new GradeId(studentId,classId);
        this.regularScore = regularScore;
        this.midtermScore = midtermScore;
        this.labScore = labScore;
        this.finalScore = finalScore;
        this.totalScore = totalScore;
        this.gradeDate = gradeDate;
    }

    public Grade() {

    }
    // Getters and Setters

    public GradeId getGradeId() {
        return gradeId;
    }

    public void setGradeId(GradeId gradeId) {
        this.gradeId = gradeId;
    }

    // Getter 和 Setter 方法
    public Integer getRegularScore() {
        return regularScore;
    }

    public void setRegularScore(Integer regularScore) {
        this.regularScore = regularScore;
    }

    public Integer getMidtermScore() {
        return midtermScore;
    }

    public void setMidtermScore(Integer midtermScore) {
        this.midtermScore = midtermScore;
    }

    public Integer getLabScore() {
        return labScore;
    }

    public void setLabScore(Integer labScore) {
        this.labScore = labScore;
    }

    public Integer getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(Integer finalScore) {
        this.finalScore = finalScore;
    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    public Date getGradeDate() {
        return gradeDate;
    }

    public void setGradeDate(Date gradeDate) {
        this.gradeDate = gradeDate;
    }
    public String getStudentId() {
        return gradeId.getStudentId();
    }

    public void setStudentId(String studentId) {
        gradeId.setStudentId(studentId);
    }

    public String getClassId() {
        return gradeId.getClassId();
    }

    public void setClassId(String ClassId) {
        gradeId.setClassId(ClassId);
    }
}
