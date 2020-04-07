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

public class LoginController{
    protected static final String FOUTMELDING_INLOGNAAM = "De opgegeven inlognaam is niet correct";
    protected static final String FOUTMELDING_WACHTWOORD = "Het opgegeven wachtwoord is niet correct";
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
            verkeerdeInlogGegevens.setContentText(FOUTMELDING_INLOGNAAM);
            verkeerdeInlogGegevens.show();
        }
        else if (!user.getPassword().equals(passwordField.getText())){
            Alert verkeerdeInlogGegevens = new Alert(Alert.AlertType.ERROR);
            verkeerdeInlogGegevens.setContentText(FOUTMELDING_WACHTWOORD);
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
