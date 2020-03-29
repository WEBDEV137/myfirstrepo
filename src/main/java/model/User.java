package model;

import java.util.ArrayList;
import java.util.Collections;

public class User {
    //CONSTANTEN
    protected final static String QUIZ_BEHEREN = "Quiz beheren";
    protected final static String QUIZ_AANMAKEN_WIJZIGEN = "Quiz aanmaken/wijzigen";
    protected final static String VOORTGANG_BEKIJKEN = "Voortgang studenten bekijken";
    protected final static String QUIZ_DASHBOARD = "Quiz Dashboard";
    protected final static String VOORTGANG_DASHBOARD = "Voortgang Dashboard";

    //VARIABELEN
    private String inlognaam;
    private String naam;
    private String wachtwoord;
    private String rol;
    protected ArrayList<String> tasks;


    //CONSTRUCTOR
    public User(String inlognaam, String wachtwoord, String rol){
        setInlognaam(inlognaam);
        setWachtwoord(wachtwoord);
        setRol(rol);
    }
    //SETTERS
    public void setInlognaam(String inlognaam) {
        this.inlognaam = inlognaam;
    }
    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
   //GETTERS
    public String getInlognaam() {
        return inlognaam;
    }
    public String getWachtwoord() {
        return wachtwoord;
    }
    public String getRol() {
        return rol;
    }
    public ArrayList<String> getAllTasks() {
        return tasks;
    }

    //METHODEN

    @Override
    public String toString() {
        return  "inlognaam: " + inlognaam + "\nwachtwoord: " + wachtwoord + "\nrol: " + rol;
    }
}
