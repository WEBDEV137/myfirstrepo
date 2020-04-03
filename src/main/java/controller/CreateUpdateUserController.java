package controller;

import database.mysql.CourseDAO;
import database.mysql.DBAccess;
import database.mysql.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import model.Course;
import model.Group;
import model.User;
import view.Main;

import java.util.ArrayList;
import java.util.List;

public class CreateUpdateUserController {
    private UserDAO udao;
    private DBAccess db;
    private User user;

    @FXML
    public Label titleLabel;
    @FXML
    public TextField userIdTextfield;
    @FXML
    public TextField userNameTextfield;
    @FXML
    public TextField nameTextfield;
    @FXML
    public TextField prefixTextfield;
    @FXML
    public TextField surnameTextfield;
    @FXML
    public TextField rolNameTextfield;
    @FXML
    public TextField passwordTextfield;

    public CreateUpdateUserController() {
        super();
        this.db = Main.getDBaccess();
        this.udao = new UserDAO(db);

    }


    @FXML
    public void doMenu(ActionEvent e) {
        Main.getSceneManager().showWelcomeScene(Main.getCurrentUser());
    }
    @FXML
    public void doCreateUpdateUser(ActionEvent e) {
        createUser();
        if (user != null) {
            if (userIdTextfield.getText().equals(("userId"))) {
                udao.storeOne(user);
                userIdTextfield.setText(String.valueOf(user.getUserId()));
                Alert opgeslagen = new Alert(Alert.AlertType.INFORMATION);
                opgeslagen.setContentText("User opgeslagen");
                opgeslagen.show();
            } else {
                int id = Integer.valueOf(userIdTextfield.getText());
                user.setUserId(id);
                udao.updateUser(user);
                Alert gewijzigd = new Alert(Alert.AlertType.INFORMATION);
                gewijzigd.setContentText("User gewijzigd");
                gewijzigd.show();
            }
        }

    }

    public void setup(User user) {
        if (user == null) {
            titleLabel.setText("Nieuwe user");
            userIdTextfield.getText();
            userNameTextfield.setText("");
            nameTextfield.setText("");
            prefixTextfield.setText("");
            surnameTextfield.setText("");
            rolNameTextfield.setText("");
            passwordTextfield.setText("");
        } else {
            titleLabel.setText("Update user");
            userIdTextfield.setText(String.valueOf(user.getUserId()));
            rolNameTextfield.setText(user.getRolName());
            userNameTextfield.setText(user.getUserName());
            passwordTextfield.setText(user.getPassword());
            nameTextfield.setText(user.getName());
            prefixTextfield.setText(user.getPrefix());
            surnameTextfield.setText(user.getSurname());
        }
    }

    private void createUser() {
        StringBuilder warningText = new StringBuilder();
        boolean correctInvoer = true;
        String rolName = rolNameTextfield.getText();
        String userName = userNameTextfield.getText();
        if (userName.isEmpty()) {
            warningText.append("Je moet user name invullen!!\n");
            correctInvoer = false;
        }
        String password = passwordTextfield.getText();
        String name = nameTextfield.getText();
        String prefix = prefixTextfield.getText();
        String surname = surnameTextfield.getText();
        if (!correctInvoer) {
            Alert foutmelding = new Alert(Alert.AlertType.ERROR);
            foutmelding.setContentText(warningText.toString());
            foutmelding.show();
            user = null;
        } else {
            user = new User(rolName, userName, password, name, prefix, surname);
        }
    }
}
