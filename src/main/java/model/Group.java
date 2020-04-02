package model;

import java.util.ArrayList;

public class Group {
    //variabelen
private int groupId;
private String groupName;
private Course course;
private User user; //dit is de docent
private ArrayList<User> studenten; //dit zijn de studenten

    // all args constructor
    public Group(int groupId, String groupName, int cursusId, int userId) {
        this.groupId = groupId;
        this.groupName = groupName;
        course = new Course(cursusId, "Vul in", 0);
        user = new User(String.valueOf(userId), "Vul in", "Vul in", "Vul in", "Vul in");
        studenten = new ArrayList<>();
    }

    public Group(String groupName, int cursusId, int userId) {
        this.groupName = groupName;
        course = new Course(cursusId, "Vul in", 0);
        user = new User(String.valueOf(userId), "Vul in", "Vul in", "Vul in", "Vul in");
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //toString
    @Override
    public String toString() {
        return groupName;
    }


}

