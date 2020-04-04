package controller;

import database.mysql.DBAccess;
import database.mysql.UserDAO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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

    @FXML
    public void doLogin() {
        Main.getDBaccess().openConnection();
        System.out.println("Connection open");
        UserDAO userDAO = new UserDAO(Main.getDBaccess());
        User user = userDAO.getUserByInlognaam(nameTextField.getText());
        if (user == null || !user.getUserName().equals(nameTextField.getText())){
            Alert verkeerdeInlogGegevens = new Alert(Alert.AlertType.ERROR);
            verkeerdeInlogGegevens.setContentText("Je moet een inlognaam invoeren!!!");
            verkeerdeInlogGegevens.show();
        }
        else if (!user.getPassword().equals(passwordField.getText())){
            Alert verkeerdeInlogGegevens = new Alert(Alert.AlertType.ERROR);
            verkeerdeInlogGegevens.setContentText("Deze combinatie van inlognaam / wachtwoord is niet bekend!");
            verkeerdeInlogGegevens.show();
        }
        else{
            Main.setCurrentUser(user);
            Main.getSceneManager().showWelcomeScene(user);
            System.out.println(user);
        }
    }
    @FXML
    public void doQuit(ActionEvent event) {
        System.exit(0);
    }

}
