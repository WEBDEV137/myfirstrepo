package controller;

import database.mysql.DBAccess;
import database.mysql.UserDAO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.User;
import view.Main;

public class LoginController extends AbstractController{

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField passwordField;

    public void doLogin() {
        //maak verbinding met database met behulp van het dDBaccess object dat in de Main is aangemaakt.
        //maak userDAO met behulp van het dbAccess object
        DBAccess dbAccess = Main.getDBaccess();
        dbAccess.openConnection();
        UserDAO userDAO = new UserDAO(dbAccess);
        try {
            User user = userDAO.getUserByInlognaam(nameTextField.getText()); //Haal user uit database
            //Geef een waarschuwing als de inlopgnaam onjuist is of als het wachtwoord fout is
            // Laat anders weten dat het gelukt is en laat het wvolgende scherm zien
            if (user == null || !user.getInlognaam().equals(nameTextField.getText())){
               showAlert("Inloggen mislukt: Inlognaam komt niet voor in database", "information");
            }
            else if (!user.getWachtwoord().equals(passwordField.getText())){
                showAlert("Inloggen mislukt: Wachtwoord onjuist", "warning");
            }
            else{
                Main.getSceneManager().showWelcomeScene(user);
                showAlert("Inloggen gelukt", "confirmation");
            }
        }
        catch (Exception fout){
            fout.getMessage();
        }
    }


}
