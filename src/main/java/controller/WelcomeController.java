package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import view.Main;

public class WelcomeController {

    @FXML
    private Label welcomeLabel;
    @FXML
    private MenuButton taskMenuButton;



    public void setup(String rol) {
        switch (rol) {
            case "Student":
               voegMenuItemsToeStudent();
                break;
            case "Docent":
                //Maak custom menu items aan
                break;
            case "Administrator":
                //Maak custom menu items aan
                break;
            case "Technisch beheerder":
                voegMenuItemsToeTechnischBeheerder();
                break;
            case "Coordinator":
                //Maak custom menu items aan
                break;
        }
    }
    public void doLogout() {}

    public void voegMenuItemsToeStudent(){
        MenuItem taak1 = new MenuItem("Quiz Selecteren");
       /* taak1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.getSceneManager().showFillOutQuiz();
            }
        });*/
        taskMenuButton.getItems().add(taak1);
    }


// toevoegen taken technisch beheerder
    public void voegMenuItemsToeTechnischBeheerder(){
        MenuItem taak1 = new MenuItem("Voeg nieuwe gebruiker toe");
       /* taak1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.getSceneManager().showFillOutQuiz();
            }
        });*/
        taskMenuButton.getItems().add(taak1);

        MenuItem taak2 = new MenuItem("Beheer bestaande gebruiker");
        taskMenuButton.getItems().add(taak2);
    }
}
