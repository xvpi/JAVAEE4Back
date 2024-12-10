package com.xvpi.stugraman.beans;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "grade")
public class Grade implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private GradeId gradeId;


    @Column(name = "regular_score")
    private Integer regularScore;
    @Column(name = "midterm_score")
    private Integer midtermScore;
    @Column(name = "lab_score")
    private Integer labScore;
    @Column(name = "final_score")
    private Integer finalScore;
    @Column(name = "total_score")
    private Integer totalScore;
    @Column(name = "grade_date")
    private java.sql.Date gradeDate;

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    private Integer rank;
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    private String courseName;
    private String teacherName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
    public Grade(String studentId, String classId, int regularScore, int midtermScore, int labScore, int finalScore, int totalScore, Date gradeDate) {
        if (this.gradeId == null) {
            this.gradeId = new GradeId();
        }
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
        if (this.gradeId == null) {
            this.gradeId = new GradeId();
        }this.gradeDate = gradeDate;
    }
    public String getStudentId() {
        return gradeId.getStudentId();
    }

    public void setStudentId(String studentId) {
        if (this.gradeId == null) {
            this.gradeId = new GradeId();
        }
        gradeId.setStudentId(studentId);
    }

    public String getClassId() {
        return gradeId.getClassId();
    }

    public void setClassId(String ClassId) {
        gradeId.setClassId(ClassId);
    }
}
