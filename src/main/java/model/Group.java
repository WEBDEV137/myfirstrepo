package model;

import java.util.ArrayList;

public class Group {
    //variabelen
private int groupId;
private String groupName;
private User user; //dit is de docent
private Course course;
private ArrayList<User> studenten; //dit zijn de studenten

    // all args constructor
    public Group(int groupId, String groupName, int userId, int courseId) {
        this.groupId = groupId;
        this.groupName = groupName;
        user = new User(userId, "onbekend");
        course = new Course(courseId, "onbekend");
        studenten = new ArrayList<>();
    }

    // constructor zonder id. chainen?
    public Group(String groupName, int userId, int courseId) {
        this.groupName = groupName;
        user = new User(userId, "onbekend");
        course = new Course(courseId, "onbekend");
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
        return user.getUserId();
    }

    public void setUserId(User user) {
        this.user.setUserId(getUserId());
    }

    public int getCourseId() {
        return course.getId();
    }

    public void setCourseId(Course course) {
        this.course.setId(course.getId());
    }


    //toString
    @Override
    public String toString() {
        return groupName;
    }


}

