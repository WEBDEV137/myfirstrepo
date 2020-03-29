package model;

import java.util.ArrayList;

public class Docent extends User {
    //CONSTRUCTOR
    public Docent(String inlognaam, String wachtwoord, String rol) {
        super(inlognaam, wachtwoord, rol);
        tasks = new ArrayList<>();
        getAllTasks().add(VOORTGANG_DASHBOARD);
        getAllTasks().add(VOORTGANG_BEKIJKEN);

    }


}
