package controller;

import database.mysql.DBAccess;
import database.mysql.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
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
//        Main.getSceneManager().showWelcomeScene(null);
        Main.getSceneManager().setWindowTool();
    }
    @FXML
    public void doCreateUser(ActionEvent e) {
        Main.getSceneManager().showCreateUpdateUserScene(null);
    }
    @FXML
    public void doUpdateUser(ActionEvent e) {
        User user = userList.getSelectionModel().getSelectedItem();
        if (user == null) {
            Alert verkeerdeInlogGegevens = new Alert(Alert.AlertType.INFORMATION);
            verkeerdeInlogGegevens.setContentText("Je moet een user kiezen!!!");
            verkeerdeInlogGegevens.show();
            return;
        }
        Main.getSceneManager().showExistingCustomerScene(user);
    }
    @FXML
    public void doDeleteUser() {}
}
