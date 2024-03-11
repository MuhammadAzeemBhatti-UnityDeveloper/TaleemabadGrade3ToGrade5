package com.orenda.taimo.grade3tograde5.Models;

public class SubjectLibraryModel {
    int id;
    String subject;
    String topicEnglish;
    String topicUrdu;
    String subtopicEnglish;
    String subtopicUrdu;
    String path;
    String grade;
    int status;
    String r_grade;
    int marks;

    public SubjectLibraryModel(String subject, String topicEnglish, String topicUrdu, String subtopicEnglish, String subtopicUrdu, String path, String grade) {
        this.subject = subject;
        this.topicEnglish = topicEnglish;
        this.topicUrdu = topicUrdu;
        this.subtopicEnglish = subtopicEnglish;
        this.subtopicUrdu = subtopicUrdu;
        this.path = path;
        this.grade = grade;
    }

    public SubjectLibraryModel() {

    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getR_grade() {
        return r_grade;
    }

    public void setR_grade(String r_grade) {
        this.r_grade = r_grade;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTopicEnglish() {
        return topicEnglish;
    }

    public void setTopicEnglish(String topicEnglish) {
        this.topicEnglish = topicEnglish;
    }

    public String getTopicUrdu() {
        return topicUrdu;
    }

    public void setTopicUrdu(String topicUrdu) {
        this.topicUrdu = topicUrdu;
    }

    public String getSubtopicEnglish() {
        return subtopicEnglish;
    }

    public void setSubtopicEnglish(String subtopicEnglish) {
        this.subtopicEnglish = subtopicEnglish;
    }

    public String getSubtopicUrdu() {
        return subtopicUrdu;
    }

    public void setSubtopicUrdu(String subtopicUrdu) {
        this.subtopicUrdu = subtopicUrdu;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
