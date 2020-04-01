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
         taak1.setOnAction(actionEvent -> Main.getSceneManager().showManageQuizScene());
        MenuItem taak2 = new MenuItem("Beheer vragen quiz");
        taak2.setOnAction(actionEvent -> Main.getSceneManager().showManageQuestionsScene());
        MenuItem taak3 = new MenuItem("Ga naar Dashboard");
        MenuItem taak4 = new MenuItem("Create/Update quiz");
        taak1.setOnAction(e -> Main.getSceneManager().showManageQuizScene());
        taak4.setOnAction(e -> Main.getSceneManager().showCreateUpdateQuizScene(null));
        taak3.setOnAction(actionEvent -> Main.getSceneManager().showCoordinatorDashboard());
        taskMenuButton.getItems().add(taak1);
        taskMenuButton.getItems().add(taak2);
        taskMenuButton.getItems().add(taak3);
        taskMenuButton.getItems().add(taak4);
    }

<<<<<<< HEAD
    public void voegMenuItemsToeAdministrator() {
        MenuItem taak1 = new MenuItem("Cursussen beheren");
        taak1.setOnAction(e -> Main.getSceneManager().showManageCoursesScene());
        MenuItem taak2 = new MenuItem("Groepen beheren");
        taak2.setOnAction(e -> Main.getSceneManager().showManageGroupsScene());
=======
    // hier knop invoegen om naar controlscherm te gaan
    public void voegMenuItemsToeAdministrator(){
        MenuItem taak1 = new MenuItem("Cursussen beheren");
        taak1.setOnAction((new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        Main.getSceneManager().showManageCoursesScene();
                    }
                }));
>>>>>>> suzanne
        taskMenuButton.getItems().add(taak1);
        MenuItem taak2 = new MenuItem("Groepen beheren");
        taak1.setOnAction((new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Main.getSceneManager().showManageGroupsScene();
            }
        }));
        taskMenuButton.getItems().add(taak2);
    }

<<<<<<< HEAD
    public void voegMenuItemsToeTechnischBeheerder() {
        MenuItem taak1 = new MenuItem("Voeg nieuwe gebruiker toe");
=======


// toevoegen taken technisch beheerder
    public void voegMenuItemsToeTechnischBeheerder(){
        MenuItem taak1 = new MenuItem("Voeg nieuwe gebruiker toe");
/*        taak1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.getSceneManager().showFillOutQuiz();
            }
        });*/
>>>>>>> suzanne
        taskMenuButton.getItems().add(taak1);
        taak1.setOnAction(e -> Main.getSceneManager().showCreateUpdateUserScene(null));
        MenuItem taak2 = new MenuItem("Beheer bestaande gebruiker");
        taskMenuButton.getItems().add(taak2);
    }
    public void voegMenuItemsToeDocent () {
            MenuItem taak1 = new MenuItem("Bekijk voortgang");
            taskMenuButton.getItems().add(taak1);
        }
}
