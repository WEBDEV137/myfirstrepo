package model;

import java.util.ArrayList;
import java.util.Collections;

public class User {
    //CONSTANTEN
    protected final static String QUIZ_BEHEREN = "Manage Quiz";
    protected final static String QUIZ_AANMAKEN_WIJZIGEN = "Create/ update quiz";
    protected final static String VOORTGANG_BEKIJKEN = "Check results";
    protected final static String QUIZ_DASHBOARD = "Quiz Dashboard";
    protected final static String VOORTGANG_DASHBOARD = "results Dashboard";
    protected final static String COURSE_SIGN_IN_OUT ="Sign in / out Courses";
    protected final static String FILL_OUT_QUIZ ="Fill out quiz";
    protected final static String MANAGE_USERS ="Manage users";
    protected final static String CREATE_UPDATE_USERS ="Create / update users";
    protected final static String MANAGE_GROUPS ="Manage users";
    protected final static String CREATE_UPDATE_GROUPS ="Create / update users";
    protected final static String MANAGE_COURSES ="Manage courses";
    protected final static String CREATE_UPDATE_COURSES ="Create / update courses";

    //VARIABELEN
    private int id;
    private String inlognaam;
    private String wachtwoord;
    private String voornaam;
    private String tussenvoegsel;
    private String achternaam;
    private String rol;
    //Taken ArrayList - deze wordt door iedere subuser override, met zijn eigen taken
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
