package model;

import java.util.ArrayList;
import java.util.List;

public class Quiz {

    //VARIABLES
    private int id;
    private String name;
    private int succesDefinition;
    private Course course;
    private List<Question> questions;


    //CONSTRUCTOR
    public Quiz(int id, String naam, int succesDefinitie, int cursusId) {
        this.id = id;
        this.name = naam;
        this.succesDefinition = succesDefinitie;
        course = new Course(cursusId, Const.ONBEKEND_STRING);
    }
    public Quiz(){
        this.id = Const.ONBEKEND_INT;
        this.name = Const.ONBEKEND_STRING;
        this.succesDefinition = Const.ONBEKEND_INT;
        this.course = new Course();
        questions = new ArrayList<>();
    }

    //METHODS
    public void addQuestion(Question question) {
        if (questions == null) {
            questions = new ArrayList<>();
        }
        questions.add(question);
    }

    //GETTERS
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public int getSuccesDefinition() {
        return succesDefinition;
    }
    public int getCourseId() {
        return course.getId();
    }
    public List<Question> getQuestions() {
        return questions;
    }
    //SETTERS
    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSuccesDefinition(int succesDefinition) {
        this.succesDefinition = succesDefinition;
    }
    public void setCourseName(String name) {
        course.setCoursename(name);
    }
    public void setCourseId(Course course) {
        this.course.setId(course.getId());
    }

    @Override
    public String toString() {
        return String.format("%s ", name);
    }
}
