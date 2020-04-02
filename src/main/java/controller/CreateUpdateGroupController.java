package controller;

import database.mysql.DBAccess;
import database.mysql.GroupDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import model.Group;
import view.Main;

public class CreateUpdateGroupController {

    private GroupDAO groupDAO;
    private DBAccess dbAccess;

    @FXML
    private TextField groupNameTextfield;

    @FXML
    private MenuButton courseMenuButton;

    @FXML
    private MenuButton teacherMenuButton;

    @FXML
    private TextField groupNumber;


    public void setup(Group group) {
/*        // setup: moet anders zijn afhankelijk of er wel of niet een groep is geselecteerd
        Group group = groupList.getSelectionModel().getSelectedItem();
        if (group == null)
        groupNameTextfield.setText(String.valueOf(customer.getCustomerId()));
        courseMenuButton.setText(customer.getInitials());
        teacherMenuButton.setText(customer.getPrefix());
        groupNumber.setText(customer.getSurName());*/
    }


    public void doCreateUpdateGroup(ActionEvent event) {
    }




    @FXML
    public void doStore(ActionEvent actionEvent) {
    }

    @FXML
    public void doBackToList(ActionEvent actionEvent) {
/*        this.db.closeConnection();
        System.out.println("Connection closed");*/
        Main.getSceneManager().showManageGroupsScene();
    }

    @FXML
    public void doBackToMenu(ActionEvent actionEvent) {
        /*        // hoe krijg je hier de ingelogde user in?
        User user = ;
        Main.getSceneManager().showWelcomeScene(User user);*/
        Main.getSceneManager().showLoginScene();
/*        this.db.closeConnection();
        System.out.println("Connection closed");
        ApplicationLauncher.getSceneManager().showWelcomeScene();*/
    }



}