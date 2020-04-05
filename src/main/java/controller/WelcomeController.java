package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import model.User;
import view.Main;

import java.util.Optional;

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
        Main.getDBaccess().closeConnection();
        System.out.println("Connection closed");
        Main.getSceneManager().showLoginScene();
    }

    public void voegMenuItemsToeStudent() {
        MenuItem taak1 = new MenuItem("Quiz invullen");
        taak1.setOnAction(actionEvent -> Main.getSceneManager().showSelectQuizForStudent());
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
        taak2.setOnAction(actionEvent -> Main.getSceneManager().showManageQuizScene());
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
        taak1.setOnAction(e -> Main.getSceneManager().showCreateUpdateUserScene(null));
        taskMenuButton.getItems().add(taak1);
        MenuItem taak2 = new MenuItem("Beheer bestaande gebruiker");
        taak2.setOnAction(e -> Main.getSceneManager().showManageUserScene());
        taskMenuButton.getItems().add(taak2);
    }


    public void voegMenuItemsToeDocent() {
        MenuItem taak1 = new MenuItem("Bekijk voortgang");
        taskMenuButton.getItems().add(taak1);
    }

    /**
     * Confirmation dialogue
     * do you want to logOUT?
     */
    public void doLogOutConfirmation() {
         String ARE_YOU_SURE = "Weet u zeker dat u wilt uitloggen?";
         String CLICK_CONTINUE ="klik OK om door te gaan";

        Alert okCancelDialogue = new Alert(Alert.AlertType.CONFIRMATION);
        okCancelDialogue.initModality(Modality.APPLICATION_MODAL); //Achtegrpond scherm wordt onbruikbaar gemaakt.
        okCancelDialogue.initOwner(Main.getPrimaryStage()); //show,
        okCancelDialogue.setTitle(Main.QUISMASTER);
        okCancelDialogue.setHeaderText(ARE_YOU_SURE);
        okCancelDialogue.setContentText(CLICK_CONTINUE);

        Optional<ButtonType> result = okCancelDialogue.showAndWait();
        if (result.get() == ButtonType.OK) {
            doLogout();
        }
    }
}
