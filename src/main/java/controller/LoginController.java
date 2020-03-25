package controller;

import database.mysql.UserDAO;
import javafx.fxml.FXML;
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
        User user = userDAO.getUserByInlognaamEnWachtwoord(nameTextField.getText(), passwordField.getText());
        System.out.println(user);

    }

    public void doQuit() {}
}
