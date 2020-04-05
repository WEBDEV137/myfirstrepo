package model;

import java.util.ArrayList;

public class Group {
    //variabelen
private int groupId;
private String groupName;
private int userId; //dit is de docent
private int courseId;
private ArrayList<User> studenten; //dit zijn de studenten

    // all args constructor
    public Group(int groupId, String groupName, int userId, int courseId) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.userId = userId;
        this.courseId = courseId;
        studenten = new ArrayList<>();
    }

    // constructor zonder id. chainen?
    public Group(String groupName, int userId, int courseId) {
        this.groupName = groupName;
        this.userId = userId;
        this.courseId = courseId;
        studenten = new ArrayList<>();
    }


    // getters en setters
    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    //toString
    @Override
    public String toString() {
        return groupName;
    }


}

