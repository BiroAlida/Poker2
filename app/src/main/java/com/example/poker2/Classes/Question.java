package com.example.poker2.Classes;

import java.util.ArrayList;

public class Question {

    private String questionId;
    private String groupId;
    private String question;
    private String isActive;

    public Question() {
    }

    public Question(String questionId, String groupId, String question, String isActive) {
        this.questionId = questionId;
        this.groupId = groupId;
        this.question = question;
        this.isActive = isActive;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    /*public Question(String questionId, String groupId, String question, Integer isActive) {
        this.questionId = questionId;
        this.groupId = groupId;
        this.question = question;
        this.isActive = isActive;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }*/
}