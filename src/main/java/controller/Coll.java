package controller;

import javafx.application.Platform;
import javafx.scene.control.*;
import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.TimeUnit;

/**
 * Collectie van Methoden die door alle klassen gebruikt kan worden
 */

public class Coll {

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
        public static  <T> void populateListView(ListView listView, List<T> list) {
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
        public static boolean checkIfNameAllowed(String inputText, String notAllowedCharacters) {
            for (int index = 0; index < notAllowedCharacters.length(); index++) {
                if (inputText.indexOf(notAllowedCharacters.charAt(index)) != -1) {
                    return false;
                }
            }
            return true;
        }
        /**
         * Laat een alert zien met de ingegeven tekst als 1e en 2e parameter en ingegeven alerttype (als String) als derde
         * Kies als alerttype: "error" of "conformation" of "warning" "information"
         *
         */
        public static void showAlert(String header, String content, String alertType){
            Alert alert;
            if  (alertType.toLowerCase().equals(Const.ALERTTYPE_WAARSCHUWING))
            {alert = new Alert(Alert.AlertType.WARNING);}
            else if  (alertType.toLowerCase().equals(Const.ALERTTYPE_FOUTMELDING))
            {alert = new Alert(Alert.AlertType.ERROR);}
            else if  (alertType.toLowerCase().equals(Const.ALERTTYPE_BEVESTIGING))
            {alert = new Alert(Alert.AlertType.CONFIRMATION);}
            else
            {alert = new Alert(Alert.AlertType.INFORMATION);}
            alert.setTitle(Const.PROGRAMMA_NAAM);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.show();
        }

        /**
         * geef alert aan de gebruiker dat quizmaster gaat afgesloten word. En sluit quismaster af.
         */
        public void doQuit() {
            showAlert(Const.STATUSBERICHT_AFSLUITEN,Const.TOTZIENS_GROET, Const.ALERTTYPE_INFORMATION);
            try {
                TimeUnit.SECONDS.sleep(Const.PAUZEER_LENGTE);  //laat java aantal seconden pauzeren
            }
            catch(Exception fout){
                fout.getMessage();
            }
            Platform.exit();
        }
}


