package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import model.User;
import view.Main;

public class WelcomeController {

    @FXML
    private Label welcomeLabel;
    @FXML
    private MenuButton taskMenuButton;


    public void setup(User user) {
        char userName = user.getInlognaam().toUpperCase().charAt(0);
        welcomeLabel.setText("Welkom " + userName + user.getInlognaam().toLowerCase().substring(1) + "\nUw rol is: " + user.getRol());


        switch (user.getRol()) {
            case "Student":
                voegMenuItemsToeStudent();
                break;
            case "Docent":
                voegMenuItemsToeDocent();
                break;
            case "Administrator":
                voegMenuItemsToeAdministrator();
                break;
            case "Technisch beheerder":
                voegMenuItemsToeTechnischBeheerder();
                break;
            case "Coordinator":
                voegMenuItemsToeCoordinator();
                voegMenuItemsToeDocent();
                break;
        }
    }

    public void doLogout() {
        Main.getSceneManager().showLoginScene();
    }

    public void voegMenuItemsToeStudent() {
        MenuItem taak1 = new MenuItem("Quiz invullen");
        taskMenuButton.getItems().add(taak1);
        MenuItem taak2 = new MenuItem("In- en uitschrijven cursus");
        taskMenuButton.getItems().add(taak2);
    }

    public void voegMenuItemsToeCoordinator() {
        MenuItem taak1 = new MenuItem("Beheer quizzen");
        MenuItem taak2 = new MenuItem("Beheer vragen quiz");
        MenuItem taak3 = new MenuItem("Ga naar Dashboard");
        MenuItem taak4 = new MenuItem("Create/Update quiz");
        taak1.setOnAction(e -> Main.getSceneManager().showManageQuizScene());
        taak4.setOnAction(e -> Main.getSceneManager().showCreateUpdateQuizScene(null));
        taskMenuButton.getItems().add(taak1);
        taskMenuButton.getItems().add(taak2);
        taskMenuButton.getItems().add(taak3);
        taskMenuButton.getItems().add(taak4);
    }

    public void voegMenuItemsToeAdministrator() {
        MenuItem taak1 = new MenuItem("Cursussen beheren");
        MenuItem taak2 = new MenuItem("Groepen beheren");
        taskMenuButton.getItems().add(taak1);
        taskMenuButton.getItems().add(taak2);
    }

    public void voegMenuItemsToeTechnischBeheerder() {
        MenuItem taak1 = new MenuItem("Voeg nieuwe gebruiker toe");
        taskMenuButton.getItems().add(taak1);
        MenuItem taak2 = new MenuItem("Beheer bestaande gebruiker");
        taskMenuButton.getItems().add(taak2);
    }
    public void voegMenuItemsToeDocent () {
            MenuItem taak1 = new MenuItem("Bekijk voortgang");
            taskMenuButton.getItems().add(taak1);
        }
}




