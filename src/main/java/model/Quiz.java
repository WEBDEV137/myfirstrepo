package model;

import database.mysql.DBAccess;

import java.util.ArrayList;

public class Quiz {

    //VARIABLES
    private int id;
    private String name;
    private int succesDefinition;
    private Course course;
    private ArrayList<Question> questions;

    //CONSTRUCTOR
    public Quiz(int id, String naam, int succesDefinitie, int cursusId) {
        this.id = id;
        this.name = naam;
        this.succesDefinition = succesDefinitie;
        course = new Course(cursusId, "nogNietOpgehaaldUitDatabase", 0);
    }

    //METHODS
    public void addQuestion(Question question) {
        if (questions == null) {
            questions = new ArrayList<>();
        }
        questions.add(question);
    }
    public void addAllQuestions(ArrayList<Question> questions) {
        for (Question question : questions){
            addQuestion(question);
        }
    }
    public ArrayList<Question> getQuestions() {
        return questions;
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

    //SETTERS
    public void setName(String name) {
        this.name = name;
    }
    public void setSuccesDefinition(int succesDefinition) {
        this.succesDefinition = succesDefinition;
    }

    public void setAllQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
    //SETTERS voor de Course waar de quiz bij hoort
    public void setCourseName(String name) {
        course.setCoursename(name);
    }
    public void setCourseCoordinatorId(int coordinatorId) {
        course.setCoordinatorid(coordinatorId);
    }



    @Override
    public String toString() {
        return String.format("id:   %d            |              Quiz name:   %s            |              Course name:     %s", id, name, course.getCoursename());
    }
}
