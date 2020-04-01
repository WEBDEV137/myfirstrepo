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
        welcomeLabel.setText("Welcome " + user.getName() + "\nUw rol is " + user.getRolName());
        switch (user.getRolName()) {
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
                voegMenuItemsToeTechnischBeheerder(user);
                break;
            case "Coordinator":
                voegMenuItemsToeCoordinator(user);
                voegMenuItemsToeDocent();
                break;
        }
    }

    public void doLogout() {
        Main.getSceneManager().showLoginScene();
    }

    public void voegMenuItemsToeStudent() {
        MenuItem taak1 = new MenuItem("Quiz invullen");
        MenuItem taak2 = new MenuItem("In- en uitschrijven cursus");
        taskMenuButton.getItems().add(taak1);
        taskMenuButton.getItems().add(taak2);
    }

    public void voegMenuItemsToeCoordinator(User user) {
        MenuItem taak1 = new MenuItem("Ga naar Dashboard");
        MenuItem taak2 = new MenuItem("Beheer quizzen");
        MenuItem taak3 = new MenuItem("Beheer vragen");
        MenuItem taak4 = new MenuItem("Create/Update quiz");
        MenuItem taak5 = new MenuItem("Create/Update vraag");
        taak1.setOnAction(actionEvent -> Main.getSceneManager().showCoordinatorDashboard());
        taak2.setOnAction(actionEvent -> Main.getSceneManager().showManageQuizScene(user));
        taak3.setOnAction(actionEvent -> Main.getSceneManager().showManageQuestionsScene());
        taak4.setOnAction(e -> Main.getSceneManager().showCreateUpdateQuizScene(null));
        taak5.setOnAction(e -> Main.getSceneManager().showCreateUpdateQuizScene(null));
        taskMenuButton.getItems().add(taak1);
        taskMenuButton.getItems().add(taak2);
        taskMenuButton.getItems().add(taak3);
        taskMenuButton.getItems().add(taak4);
        taskMenuButton.getItems().add(taak5);
    }

    public void voegMenuItemsToeAdministrator() {
        MenuItem taak1 = new MenuItem("Cursussen beheren");
        taak1.setOnAction(e -> Main.getSceneManager().showManageCoursesScene());
        taskMenuButton.getItems().add(taak1);
        MenuItem taak2 = new MenuItem("Groepen beheren");
        taak2.setOnAction(e -> Main.getSceneManager().showManageGroupsScene());
        taskMenuButton.getItems().add(taak2);
    }



// toevoegen taken technisch beheerder
    public void voegMenuItemsToeTechnischBeheerder(User user){
        MenuItem taak1 = new MenuItem("Voeg nieuwe gebruiker toe");
        taak1.setOnAction(e -> Main.getSceneManager().showCreateUpdateUserScene(user));
        taskMenuButton.getItems().add(taak1);
        MenuItem taak2 = new MenuItem("Beheer bestaande gebruiker");
        taak2.setOnAction(e -> Main.getSceneManager().showManageUserScene());
        taskMenuButton.getItems().add(taak2);
    }


    public void voegMenuItemsToeDocent() {
        MenuItem taak1 = new MenuItem("Bekijk voortgang");
        taskMenuButton.getItems().add(taak1);
    }
}
