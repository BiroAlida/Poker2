package com.example.poker2;

import java.util.ArrayList;

public class Group {

    private String groupId;
    private String groupName;
    private ArrayList<String> questions;

    public Group() {
    }

    public Group(String groupId, String groupName, ArrayList<String> questions) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.questions = questions;
    }


    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<String> questions) {
        this.questions = questions;
    }
}

