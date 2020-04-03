package model;

import java.util.List;

public class Gebruiker {
    private String gebruikerNaam;
    private String naam;
    private String tussenvoegsels;
    private String achternaam;
    private String rol;
    private String wachtwoord;
//    public List<Gebruiker> lijstGebruikers;

    public Gebruiker(String gebruikerNaam, String naam, String tussenvoegsels, String achternaam, String rol, String wachtwoord) {
        this.gebruikerNaam = gebruikerNaam;
        this.naam = naam;
        this.tussenvoegsels = tussenvoegsels;
        this.achternaam = achternaam;
        this.rol = rol;
        this.wachtwoord = wachtwoord;
    }

    public Gebruiker(String gebrukerNaam, String rol) {
        this.gebruikerNaam = gebrukerNaam;
        this.rol = rol;
    }

    public String toString() {
        StringBuilder resultString = new StringBuilder();
        resultString.append("User name: " + gebruikerNaam+ " ");
        resultString.append("Naam: " + naam + " ");
        if (tussenvoegsels != null) {
            resultString.append("Tussenvoegsels: " + tussenvoegsels + " ");
        }
        resultString.append("Achternaam: " + achternaam + " " );
        resultString.append("Rol: " + rol + " ");
        resultString.append("Wachtwoord: " + wachtwoord + " ");
        return resultString.toString();
    }



    public String getGebruikerNaam() {
        return gebruikerNaam;
    }

    public void setGebruikerNaam(String gebruikerNaam) {
        this.gebruikerNaam = gebruikerNaam;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getTussenvoegsels() {
        if (tussenvoegsels != null) {
            return tussenvoegsels;
        } else {
            return "";
        }
    }

    public void setTussenvoegsels(String tussenvoegsels) {
        this.tussenvoegsels = tussenvoegsels;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }
}
