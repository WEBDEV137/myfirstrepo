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
        User user = userDAO.getUserByInlognaam(nameTextField.getText());

        if (user == null || !user.getUserName().equals(nameTextField.getText())){
            Alert verkeerdeInlogGegevens = new Alert(Alert.AlertType.ERROR);
            verkeerdeInlogGegevens.setContentText("Please een juiste user name invoeren!!!");
            verkeerdeInlogGegevens.show();
        }
        else if (!user.getPassword().equals(passwordField.getText())){
            Alert verkeerdeInlogGegevens = new Alert(Alert.AlertType.ERROR);
            verkeerdeInlogGegevens.setContentText("Please een juiste password invoeren!!!");
            verkeerdeInlogGegevens.show();
        }
        else{
            Main.getSceneManager().showWelcomeScene(user);
            System.out.println(user);
        }

    }

    public void doQuit() {
        System.exit(0);
    }
}
