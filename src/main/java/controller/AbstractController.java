package controller;


import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.stage.Modality;
import model.*;
import view.Main;

import java.util.List;
import java.util.Optional;
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
     * Voeg alle objecten uit een List to aan een Listview (scherm-element). Generics is gebruikt zodat
     * Lists met verschillende soorten Objecten kunnen worden gebruikt.
     * @param listView
     *              scherm element ListView dat gevuld moet worden
     * @param list
     *              De list die de objecten bevat die aan de ListView toegevoegd moeten worden
     * @param <T>
     *          Type parameter
     */
    public <T> void populateListView(ListView listView, List<T> list) {
        if (listView.getItems().size() > 0) { listView.getItems().clear(); };
        for (Object o : list) {
            listView.getItems().add(o);
        }
    }
    /**
     * Checks if an input String contains any of the not allowed characters. returns false if it does
     *
     * @param inputText
     * @param notAllowedCharacters
     * @return true if the String is allowed
     */
    public boolean checkIfNameAllowed(String inputText, String notAllowedCharacters) {
        for (int index = 0; index < notAllowedCharacters.length(); index++) {
            if (inputText.indexOf(notAllowedCharacters.charAt(index)) != -1) {
                return false;
            }
        }
        return true;
    }

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
     * Laat een alert zien met de ingegeven tekst als 1e en 2e parameter en ingegeven alerttype (als String) als derde
     * Kies als alerttype: "error" of "conformation" of "warning" "information"
     *
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
     * Confirmation dialogue
     * do you want to continue?
     */
    public static void showDialogueBox(String header, String content) {
        Alert okCancelDialogue = new Alert(Alert.AlertType.CONFIRMATION);
        okCancelDialogue.initModality(Modality.APPLICATION_MODAL); //Achtegrpond scherm wordt onbruikbaar gemaakt.
        okCancelDialogue.initOwner(Main.getPrimaryStage()); //show,
        okCancelDialogue.setTitle(PROGRAMMA_NAAM);
        okCancelDialogue.setHeaderText(header);
        okCancelDialogue.setContentText(content);

        Optional<ButtonType> result = okCancelDialogue.showAndWait();
        if (result.get() == ButtonType.OK) {
            //storeQuiz();
        } else if (result.get() == ButtonType.CANCEL) {
            //
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
