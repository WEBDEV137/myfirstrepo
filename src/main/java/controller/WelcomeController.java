package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import model.Quiz;
import model.User;
import view.Main;

public class WelcomeController extends AbstractController {

    @FXML
    private Label welcomeLabel;
    @FXML
    private MenuButton taskMenuButton;


    public void setup(User user) {
        //Laat aangepaste welkomstekst zien
        welcomeLabel.setText("Welkom " + user.getInlognaam() + "\nUw rol is: " + user.getRol());
        //Maak aan de hand van de rol van de gebruiker het juiste type sub-gebruiker
       User subUser = createSubUserFromUser(user);
        //Voeg alle knoppen toe aan het menu bijbehorend bijde rol van de gebruiker
        addAllButtonsToDropdownMenu(subUser, taskMenuButton);

        System.out.println(user.getAllTasks());
    }

    public void doLogout() {
        Main.getSceneManager().showLoginScene();
    }
}

