package controller;

import database.mysql.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.User;
import view.Main;

public class LoginController {

    @FXML
    private TextField nameTextField;
    @FXML
    private TextField passwordField;

    public void doLogin() {
        Main.getDBaccess().openConnection();
        System.out.println("Connection open");
        UserDAO userDAO = new UserDAO(Main.getDBaccess());
        try {
            User user = userDAO.getUserByInlognaam(nameTextField.getText());
            System.out.println(user);
            if (nameTextField.getText().equals(user.getUserName()) && passwordField.getText().equals(user.getPassword())) {
                Main.getSceneManager().showWelcomeScene(user);
            }
        }
        catch (Exception fout){
//            fout.getMessage();
            Alert verkeerdeInlogGegevens = new Alert(Alert.AlertType.ERROR);
            verkeerdeInlogGegevens.setContentText("Onjuiste inloggevens");
            verkeerdeInlogGegevens.show();
        }
        //WelcomeController.setup(user.getRol());

        //primaryStage.show();

    }

    public void doQuit() {
        System.exit(0);
    }
}
