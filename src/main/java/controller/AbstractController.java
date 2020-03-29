package controller;


import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import model.*;
import view.Main;

import java.util.concurrent.TimeUnit;

public abstract class AbstractController {
    //VARIABELEN
    protected static final String ROL_STUDENT = "Student";
    protected static final String ROL_DOCENT = "Docent";
    protected static final String ROL_COORDINATOR = "Coordinator";
    protected static final String ROL_ADMINISTRATOR = "Administrator";
    protected static final String ROL_TECHNISCH_BEHEERDER = "Technisch beheerder";
    protected static final String ALERTTYPE_WAARSCHUWING = "warning";
    protected static final String ALERTTYPE_FOUTMELDING = "error";
    protected static final String ALERTTYPE_BEVESTIGING = "confirmation";



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
    public static void showAlert(String alertText, String alertType){
        Alert alert;
        if  (alertType.toLowerCase().equals(ALERTTYPE_WAARSCHUWING))
            {alert = new Alert(Alert.AlertType.WARNING);}
        else if  (alertType.toLowerCase().equals(ALERTTYPE_FOUTMELDING))
             {alert = new Alert(Alert.AlertType.ERROR);}
        else if  (alertType.toLowerCase().equals(ALERTTYPE_BEVESTIGING))
             {alert = new Alert(Alert.AlertType.CONFIRMATION);}
        else
             {alert = new Alert(Alert.AlertType.INFORMATION);}
        alert.setContentText(alertText);
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
                Student student = new Student(user.getInlognaam(), user.getWachtwoord(), user.getRol());
                return student;
            case ROL_DOCENT:
                Docent docent = new Docent(user.getInlognaam(), user.getWachtwoord(), user.getRol());
                return docent;
            case ROL_COORDINATOR:
                Coordinator coordinator = new Coordinator(user.getInlognaam(), user.getWachtwoord(), user.getRol());
                return coordinator;
                case ROL_ADMINISTRATOR:
                Administrator administrator = new Administrator(user.getInlognaam(), user.getWachtwoord(), user.getRol());
                return  administrator;
            case ROL_TECHNISCH_BEHEERDER:
                TechnischBeheerder technischBeheerder = new TechnischBeheerder(user.getInlognaam(), user.getWachtwoord(), user.getRol());
                return technischBeheerder;
            default:
                return null;
        }
    }

    /**
     * geef alert aan de gebruiker dat quizmaster gaat afgesloten word. En sluit quismaster af.
     */
    public void doQuit() {
        showAlert("U verlaat quizmaster, tot de volgende keer", "information");
        try {
            TimeUnit.SECONDS.sleep(3);  //laat java 3s pauzeren
        }
        catch(Exception fout){
            fout.getMessage();
        }
        Platform.exit(); // Replaced System.exit(0), wegens nettere afhandeling van afsluiten;

    }


}
