package controller;

import database.mysql.DBAccess;
import database.mysql.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.User;
import view.Main;

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
        this.udao = udao;
        this.db = db;
    }

    public void setup() {
        titleLabel.setText("Update user");
        userIdTextfield.setText(String.valueOf(user.getUserId()));
        userNameTextfield.setText(user.getUserName());
        nameTextfield.setText(user.getUserName());
        prefixTextfield.setText(user.getPrefix());
        surnameTextfield.setText(user.getSurname());
        rolNameTextfield.setText(user.getRolName());
        passwordTextfield.setText(user.getPassword());
    }

    public void doMenu(ActionEvent e) {
        Main.getSceneManager().showWelcomeScene(user);
    }

    public void doCreateUpdateUser(ActionEvent e) {
        createUser();
        if (user != null) {
            if (userIdTextfield.getText().equals(("id"))) {
                udao.storeUser(user);
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

    private void createUser() {
        StringBuilder warningText = new StringBuilder();
        boolean correctInvoer = true;
        String userName = userNameTextfield.getText();
        String name = nameTextfield.getText();
        String prefix = prefixTextfield.getText();
        String surname = surnameTextfield.getText();
        String rolName = rolNameTextfield.getText();
        String password = passwordTextfield.getText();

        if (userName.isEmpty()) {
            warningText.append("Je moet user name invoelen!!\n");
            correctInvoer = false;
        }
        if (!correctInvoer) {
            Alert foutmelding = new Alert(Alert.AlertType.ERROR);
            foutmelding.setContentText(warningText.toString());
            foutmelding.show();
            user = null;
        } else {
            user = new User(rolName,userName,password,name,prefix,surname);
        }
    }
}
