package controller;


import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import model.*;
import view.Main;

import java.util.concurrent.TimeUnit;

public abstract class AbstractController {
    //CONSTANTEN
    protected static final String PROGRAMMA_NAAM = "Quizmaster";
    protected static final String STATUSBERICHT_INGELOGD = "U bent succesvol ingelogd";
    protected static final String STATUSBERICHT_AFSLUITEN = "U verlaat quizmaster";
    protected static final String WELKOMS_GROET = "Welkom";
    protected static final String TOTZIENS_GROET = "Tot ziens";
    protected static final String FOUTMELDING_INLOGNAAM = "De opgegeven inlognaam is niet correct";
    protected static final String FOUTMELDING_WACHTWOORD = "Het opgegeven wachtwoord is niet correct";
    protected static final String MELDING_PROBEER_OPNIEUW = "Probeer het opnieuw";
    protected static final String UW_ROL_IS = "Uw rol is";
    protected static final String ROL_STUDENT = "Student";
    protected static final String ROL_DOCENT = "Docent";
    protected static final String ROL_COORDINATOR = "Coordinator";
    protected static final String ROL_ADMINISTRATOR = "Administrator";
    protected static final String ROL_TECHNISCH_BEHEERDER = "Technisch beheerder";
    protected static final String ALERTTYPE_WAARSCHUWING = "warning";
    protected static final String ALERTTYPE_FOUTMELDING = "error";
    protected static final String ALERTTYPE_BEVESTIGING = "confirmation";
    protected static final String ALERTTYPE_INFORMATION = "information";
    protected static final int PAUZEER_LENGTE = 3;



    //CONSTRUCTOR
    public AbstractController(){}
    //METHODEN

    /**
     * Maak een menu-item voor een dropdownmenu of menubar met als label de opgegeven teks
     * *
     */
    public  MenuItem createMenuItem(String menuItem){
        return new MenuItem(menuItem);
    }
     /**
     * Voeg een extra knop toe aan een dropdown menu
     *
     */
    public  void addMenuItemToMenuButton(MenuItem menuItem, MenuButton dropdownMenu){
        dropdownMenu.getItems().add(menuItem);
    }
    /**
     * Voegt alle menuitems toe aan een dropdown menu waar een gebruiker naar toe kan gaan
     *
     */
    public  void addAllButtonsToDropdownMenu(User user, MenuButton dropdownMenu){
        for (int index = 0; index < user.getAllTasks().size() ; index++) {
            String buttonLabel = user.getAllTasks().get(index);
            MenuItem menuItemToAdd = createMenuItem(buttonLabel);
            addMenuItemToMenuButton(menuItemToAdd, dropdownMenu);
        }
    }

    /**
     * Laat een alert zien met de ingegeven tekst als 1e parameter en ingegeven alerttype (als String) als tweede
     * Kies als alerttype: "error" of "conformation" of "warning"
     * bij alle andere text input wordt het alerttype op information gezet.
     */
    public static void showAlert(String header, String content, String alertType){
        Alert alert;
        if  (alertType.toLowerCase().equals(ALERTTYPE_WAARSCHUWING))
            {alert = new Alert(Alert.AlertType.WARNING);}
        else if  (alertType.toLowerCase().equals(ALERTTYPE_FOUTMELDING))
             {alert = new Alert(Alert.AlertType.ERROR);}
        else if  (alertType.toLowerCase().equals(ALERTTYPE_BEVESTIGING))
             {alert = new Alert(Alert.AlertType.CONFIRMATION);}
        else
             {alert = new Alert(Alert.AlertType.INFORMATION);}
        alert.setTitle(PROGRAMMA_NAAM);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }

    /**
     *Maakt een sub-klasse object aan (Student, Docent, Coordinator, Administrator, TechnischBeheerder)"
     * Aan de hand van de rol van de meegegeven gebruiker.
     *
     */

    public User createSubUserFromUser(User user){
        switch(user.getRol()) {
            case ROL_STUDENT:
                return new Student(user.getInlognaam(), user.getWachtwoord(), user.getRol());
            case ROL_DOCENT:
                return new Docent(user.getInlognaam(), user.getWachtwoord(), user.getRol());
            case ROL_COORDINATOR:
                return new Coordinator(user.getInlognaam(), user.getWachtwoord(), user.getRol());
                case ROL_ADMINISTRATOR:
                    return new Administrator(user.getInlognaam(), user.getWachtwoord(), user.getRol());
            case ROL_TECHNISCH_BEHEERDER:
                return new TechnischBeheerder(user.getInlognaam(), user.getWachtwoord(), user.getRol());
            default:
                return null;
        }
    }

    /**
     * geef alert aan de gebruiker dat quizmaster gaat afgesloten word. En sluit quismaster af.
     */
    public void doQuit() {
        showAlert(STATUSBERICHT_AFSLUITEN, TOTZIENS_GROET, ALERTTYPE_INFORMATION);
        try {
            TimeUnit.SECONDS.sleep(PAUZEER_LENGTE);  //laat java aantal seconden pauzeren
        }
        catch(Exception fout){
            fout.getMessage();
        }
        Platform.exit(); // Replaced System.exit(0), volgens Oracle docs the preferred way

    }


}
