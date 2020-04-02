package controller;

import database.mysql.DBAccess;
import database.mysql.GroupDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import model.Group;
import model.User;
import view.Main;

import java.util.ArrayList;
import java.util.Iterator;

public class ManageGroupsController extends AbstractController{

    private GroupDAO groupDAO;
    private DBAccess dbAccess;

    @FXML
    private ListView<Group> groupList;

// moet hier nog een warningText?

    public void setup() {
        dbAccess = Main.getDBaccess();
        dbAccess.openConnection();
        GroupDAO groupDAO = new GroupDAO(dbAccess);
        System.out.println(groupDAO.getAll());
        try {
            ArrayList<Group> allGroups = groupDAO.getAll();
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



    @FXML
    public void doMenu() {
        Main.getSceneManager().showWelcomeScene(Main.getCurrentUser());
    }


    @FXML
    public void doCreateGroup() {
        Main.getSceneManager().showCreateUpdateGroupScene(null);
    }

    @FXML
    public void doUpdateGroup() {
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
        if (group == null) {
            Alert niksGeselecteerdFout = new Alert(Alert.AlertType.ERROR);
            niksGeselecteerdFout.setContentText("Selecteer een groep.");
            niksGeselecteerdFout.show();
        } else {
            GroupDAO groupDAO = new GroupDAO(Main.getDBaccess());
            this.groupDAO.deleteGroup(group);
        }


    }





}
