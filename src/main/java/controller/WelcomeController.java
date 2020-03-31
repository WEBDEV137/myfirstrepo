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
<<<<<<< HEAD
        //Laat aangepaste welkomstekst zien
        String welkomsTekst = String.format("%s: %s\n%s: %s", WELKOMS_GROET , user.getInlognaam(), UW_ROL_IS, user.getRol() );
        welcomeLabel.setText(welkomsTekst);
        //Maak aan de hand van de rol van de gebruiker het juiste type sub-gebruiker
        User subUser = createSubUserFromUser(user);
        //Voeg alle knoppen toe aan het menu bijbehorend bijde rol van de gebruiker
        addAllButtonsToDropdownMenu(subUser, taskMenuButton);

        System.out.println(user.getAllTasks());
=======
        char userName = user.getInlognaam().toUpperCase().charAt(0);
        welcomeLabel.setText("Welkom "  + userName + user.getInlognaam().toLowerCase().substring(1) + "\nUw rol is: " + user.getRol());

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
                // voegMenuItemsToeDocent(); // Een coordinator is een docent en heeft zijn opties.
                //Maak custom menu items aan
                break;
        }
>>>>>>> 68c1d728af8a4f8add6db7a31572f21be424f4ea
    }

    public void doLogout() {
        Main.getSceneManager().showLoginScene();
    }
}

