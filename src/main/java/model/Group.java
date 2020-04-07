package model;

import java.util.ArrayList;

public class Group implements Comparable<Group> {
    //variabelen
private int groupId;
private String groupName;
private User user; //dit is de docent
private Course course;
private ArrayList<User> students; //dit zijn de studenten

    // all args constructor
    public Group(int groupId, String groupName, int userId, int courseId) {
        this.groupId = groupId;
        this.groupName = groupName;
        user = new User(userId, "onbekend");
        course = new Course(courseId, "onbekend");
        students = new ArrayList<>();
    }

    // constructor zonder groupId
    public Group(String groupName, int userId, int courseId) {
        this.groupName = groupName;
        user = new User(userId, "onbekend");
        course = new Course(courseId, "onbekend");
        students = new ArrayList<>();
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

    public int getUserId() {
        return user.getUserId();
    }

    public int getCourseId() {
        return course.getId();
    }


    //toString
    @Override
    public String toString() {
        return groupName;
    }

    @Override
    public int compareTo(Group group) {
        return groupName.compareTo(group.getGroupName());
    }

}

