package model;

import java.util.ArrayList;

public class Coordinator extends Docent{

    //CONSTRUCTOR
    public Coordinator(String inlognaam, String wachtwoord, String rol) {
        super(inlognaam, wachtwoord, rol);
        getAllTasks().add(QUIZ_DASHBOARD);
        getAllTasks().add(QUIZ_BEHEREN);
        getAllTasks().add(QUIZ_AANMAKEN_WIJZIGEN);

    }
}
