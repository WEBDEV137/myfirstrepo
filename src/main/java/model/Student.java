package model;

public class Student extends User{
    //CONSTRUCTOR
    public Student(String inlognaam, String wachtwoord, String rol) {
        super(inlognaam, wachtwoord, rol);
        getAllTasks().add(COURSE_SIGN_IN_OUT);
        getAllTasks().add(VOORTGANG_BEKIJKEN);
        getAllTasks().add(FILL_OUT_QUIZ);
    }

}
