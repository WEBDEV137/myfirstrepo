package controller;

import database.mysql.DBAccess;
import database.mysql.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import model.User;
import view.Main;

import java.util.List;

public class ManageUsersController {
    private UserDAO udao;
    private DBAccess db;
//    private User user;

    @FXML
    ListView<User> userList;

    public ManageUsersController() {
        super();
        this.db = Main.getDBaccess();
    }

    public void setup() {
        this.udao = new UserDAO(Main.getDBaccess());
        List<User> allCustomers = udao.getAllUsers();
        for (User u : allCustomers) {
            userList.getItems().add(u);
        }
    }

    @FXML
    public void doMenu(ActionEvent e) {
//        Main.getSceneManager().showWelcomeScene(user);
        Main.getSceneManager().setWindowTool();
    }
    @FXML
    public void doCreateUser(ActionEvent e) {
        Main.getSceneManager().showCreateUpdateUserScene();
    }
    @FXML
    public void doUpdateUser(ActionEvent e) {
        User user = userList.getSelectionModel().getSelectedItem();
        Main.getSceneManager().showExistingCustomerScene();
    }
    @FXML
    public void doDeleteUser() {}
}
