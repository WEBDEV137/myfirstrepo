package model;

import java.util.ArrayList;
import java.util.Collections;

public class User {

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
        Collections.sort(tasks);
        return tasks;
    }
    //METHODEN
    public User createSubUserFromUser(){
        switch(rol) {
            case "Student":
                Student student = new Student(inlognaam, wachtwoord, rol);
                return student;
            case "Docent":
                Docent docent = new Docent(inlognaam, wachtwoord, rol);
                return docent;
            case "Coordinator":
                Coordinator coordinator = new Coordinator(inlognaam, wachtwoord, rol);
                return coordinator;
            case "Administrator":
                Administrator administrator = new Administrator(inlognaam, wachtwoord, rol);
                return administrator;
            case "Technisch beheerder":
                TechnischBeheerder technischBeheerder = new TechnischBeheerder(inlognaam, wachtwoord, rol);
                return technischBeheerder;
            default:
                return null;
        }
    }


    @Override
    public String toString() {
        return  "inlognaam: " + inlognaam + "\nwachtwoord: " + wachtwoord + "\nrol: " + rol;
    }
}
