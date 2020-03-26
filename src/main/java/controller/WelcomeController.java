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
                //Maak custom menu items aan
                break;
            case "Coordinator":
                voegMenuItemsToeCoordinator();
                // voegMenuItemsToeDocent(); // Een coordinator is een docent en heeft zijn opties.
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

    public void voegMenuItemsToeCoordinator(){
        MenuItem taak1 = new MenuItem("Beheer quizzen");
        MenuItem taak2 = new MenuItem("Beheer vragen quiz");
        MenuItem taak3 = new MenuItem("Ga naar Dashboard");
        taskMenuButton.getItems().add(taak1);
        taskMenuButton.getItems().add(taak2);
        taskMenuButton.getItems().add(taak3);
    }
}
