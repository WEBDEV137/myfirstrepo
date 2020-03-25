package model;

public class User {

    //VARIABELEN
    private String inlognaam;
    private String wachtwoord;
    private String rol;

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
    //METHODEN


    @Override
    public String toString() {
        return  "inlognaam: " + inlognaam + "\nwachtwoord: " + wachtwoord + "\nrol: " + rol;
    }
}
