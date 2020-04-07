package controller;

import database.mysql.DBAccess;
import database.mysql.GroupDAO;
import database.mysql.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import model.Group;
import view.Main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class ManageGroupsController{

    private GroupDAO groupDAO;
    private DBAccess dbAccess;

    @FXML
    private ListView<Group> groupList;

    // setup, lijst van groepen tonen
    public void setup() {
        dbAccess = Main.getDBaccess();
        dbAccess.openConnection();
        GroupDAO groupDAO = new GroupDAO(dbAccess);
        System.out.println(groupDAO.getAll());
        try {
            ArrayList<Group> allGroups = groupDAO.getAll();
            Collections.sort(allGroups);
            Iterator i = allGroups.iterator();
            while (i.hasNext()) {
                Group g = (Group) i.next();
                groupList.getItems().add(g);
            }
        }
        catch (Exception fout){
            fout.getMessage();
        }
    }


// Menuknop: terug naar WelcomeScene (inlog blijft behouden)
    @FXML
    public void doMenu(ActionEvent event) {
        Main.getSceneManager().showWelcomeScene(Main.getCurrentUser());
    }


    @FXML
    public void doCreateGroup(ActionEvent event) {
        Main.getSceneManager().showCreateUpdateGroupScene(null);
    }

    @FXML
    public void doUpdateGroup(ActionEvent event) {
        Group group = groupList.getSelectionModel().getSelectedItem();
        if (group == null) {
            Alert nietGekozenFout = new Alert(Alert.AlertType.ERROR);
            nietGekozenFout.setContentText("Selecteer een groep.");
            nietGekozenFout.show();
            return;
        }
        Main.getSceneManager().showCreateUpdateGroupScene(group);
    }

    @FXML
    public void doDeleteGroup() {
        Group group = groupList.getSelectionModel().getSelectedItem();
        System.out.println(group);
        if (group == null) {
            Alert niksGeselecteerdFout = new Alert(Alert.AlertType.ERROR);
            niksGeselecteerdFout.setContentText("Selecteer een groep.");
            niksGeselecteerdFout.show();
        } else {
            GroupDAO groupDAO = new GroupDAO(Main.getDBaccess());
            groupDAO.deleteGroupByName(group.getGroupName());
            Alert verwijder = new Alert(Alert.AlertType.INFORMATION);
            verwijder.setContentText("Groep is verwijderd.");
            verwijder.show();
            Main.getSceneManager().showManageGroupsScene();
        }
    }





}
